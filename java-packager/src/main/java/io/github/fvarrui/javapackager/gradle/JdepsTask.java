package io.github.fvarrui.javapackager.gradle;

import io.github.fvarrui.javapackager.packagers.Context;
import io.github.fvarrui.javapackager.packagers.Packager;
import io.github.fvarrui.javapackager.utils.CommandUtils;
import io.github.fvarrui.javapackager.utils.JavaUtils;
import io.github.fvarrui.javapackager.utils.Logger;
import org.apache.commons.lang3.StringUtils;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JdepsTask extends DefaultPackageTask {


    @TaskAction
    public void doPackage() throws Exception {
        Packager packager = createPackager();
        Logger.info("packager: " + packager.hashCode());

        PackagePluginExtension extension = getProject().getExtensions().findByType(PackagePluginExtension.class);
        Logger.info("extension.getManifest()" + extension.getManifest());

        packager.createJar();

        String modules = getRequiredModules(packager.getLibsFolder(), packager.getJarFile());

        Logger.error("require module:" + modules);

    }


    protected String getRequiredModules(File libsFolder, File jarFile) throws Exception {

        Logger.infoIndent("Getting required modules ... ");

        File jdeps = new File(System.getProperty("java.home"), "/bin/jdeps");

        File jarLibs = null;
        if (libsFolder != null && libsFolder.exists())
            jarLibs = new File(libsFolder, "*.jar");
        else
            Logger.warn("No dependencies found!");

        List<String> modulesList;

        if (JavaUtils.getJavaMajorVersion() >= 13) {

            String modules =
                    CommandUtils.execute(
                            jdeps.getAbsolutePath(),
                            "-q",
                            "--multi-release", JavaUtils.getJavaMajorVersion(),
                            "--ignore-missing-deps",
                            "--print-module-deps",
                            jarLibs,
                            jarFile
                    );

            modulesList =
                    Arrays.asList(modules.split(","))
                            .stream()
                            .map(module -> module.trim())
                            .filter(module -> !module.isEmpty())
                            .collect(Collectors.toList());

        } else if (JavaUtils.getJavaMajorVersion() >= 9) {

            String modules =
                    CommandUtils.execute(
                            jdeps.getAbsolutePath(),
                            "-q",
                            "--multi-release", JavaUtils.getJavaMajorVersion(),
                            "--list-deps",
                            jarLibs,
                            jarFile
                    );

            modulesList =
                    Arrays.asList(modules.split("\n"))
                            .stream()
                            .map(module -> module.trim())
                            .map(module -> (module.contains("/") ? module.split("/")[0] : module))
                            .filter(module -> !module.isEmpty())
                            .filter(module -> !module.startsWith("JDK removed internal"))
                            .distinct()
                            .collect(Collectors.toList());

        } else {

            modulesList = Arrays.asList("ALL-MODULE-PATH");

        }
        if (modulesList.isEmpty()) {
            Logger.warn("It was not possible to determine the necessary modules. All modules will be included");
            modulesList.add("ALL-MODULE-PATH");
        }

        Logger.infoUnindent("Required modules found: " + modulesList);

        return StringUtils.join(modulesList, ",");
    }

}
