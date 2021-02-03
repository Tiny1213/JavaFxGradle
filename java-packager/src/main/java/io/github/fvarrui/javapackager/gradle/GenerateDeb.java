package io.github.fvarrui.javapackager.gradle;

import java.io.File;
import java.util.UUID;

import com.netflix.gradle.plugins.deb.Deb;

import io.github.fvarrui.javapackager.packagers.ArtifactGenerator;
import io.github.fvarrui.javapackager.packagers.Context;
import io.github.fvarrui.javapackager.packagers.LinuxPackager;
import io.github.fvarrui.javapackager.packagers.Packager;
import io.github.fvarrui.javapackager.utils.Logger;
import io.github.fvarrui.javapackager.utils.VelocityUtils;

/**
 * Creates a DEB package file including all app folder's content only for 
 * GNU/Linux so app could be easily distributed on Gradle context
 */
public class GenerateDeb extends ArtifactGenerator {

	public GenerateDeb() {
		super("DEB package");
	}
	
	@Override
	public File apply(Packager packager) throws Exception {
		LinuxPackager linuxPackager = (LinuxPackager) packager;
		
		if (!linuxPackager.getLinuxConfig().isGenerateDeb()) {
			Logger.info(getArtifactName() + " generation skipped by 'linuxConfig.generateDeb' property!");
			return null;
		}
		
		Logger.warn("Sorry! " + getArtifactName() + " generation is not yet available");
		return null;

//		File assetsFolder = linuxPackager.getAssetsFolder();
//		String name = linuxPackager.getName();
//		String description = linuxPackager.getDescription();
//		File appFolder = linuxPackager.getAppFolder();
//		File outputDirectory = linuxPackager.getOutputDirectory();
//		String version = linuxPackager.getVersion();
//		boolean bundleJre = linuxPackager.getBundleJre();
//		String jreDirectoryName = linuxPackager.getJreDirectoryName();
//		File executable = linuxPackager.getExecutable();
//		String organizationName = linuxPackager.getOrganizationName();
//		String organizationEmail = linuxPackager.getOrganizationEmail();
//
//		// generates desktop file from velocity template
//		File desktopFile = new File(assetsFolder, name + ".desktop");
//		VelocityUtils.render("linux/desktop.vtl", desktopFile, linuxPackager);
//		Logger.info("Desktop file rendered in " + desktopFile.getAbsolutePath());
//
//		// generated deb file
//		File debFile = new File(outputDirectory, name + "_" + version + ".deb");
//
//		Deb debTask = createDebTask();
//		debTask.setProperty("archiveFileName", debFile.getName());
//		debTask.setProperty("destinationDirectory", outputDirectory);
//		debTask.setPackageName(name.toLowerCase());
//		debTask.setPackageDescription(description);
//		debTask.setPackager(organizationName);
//		debTask.setUploaders(organizationName);
//		debTask.setMaintainer(organizationName + (organizationEmail != null ? " <" + organizationEmail + ">" : ""));
//		debTask.setPriority("optional");
//		debTask.setArchStr("amd64");
//		debTask.setDistribution("development");
//		debTask.setRelease("1");
//		
//		// installation destination
//		debTask.into("/opt/" + name);
//		
//		// includes app folder files, except executable file and jre/bin/java
//		debTask.from("build/assets/" + name + ".desktop", c -> {
//			c.into(name);
//		});
//		
//		// executable
//		debTask.from(appFolder.getParentFile(), c -> {
//			c.include(appFolder.getName() + "/" + executable.getName());
//			c.setFileMode(0755);
//		});
//
//		// java binary file
//		if (bundleJre) {
//			debTask.from(appFolder.getParentFile(), c -> {
//				c.include(appFolder.getName() + "/" + jreDirectoryName + "/bin/java");
//				c.setFileMode(0755);
//			});
//		}
//
//		// desktop file
//		debTask.from(desktopFile.getParentFile().getAbsolutePath(), c -> {
//			c.into("/usr/share/applications");
//		});
//
//		// symbolic link in /usr/local/bin to app binary
//		debTask.link("/usr/local/bin/" + name, "/opt/" + name + "/" + name, 0777);
//		
//		// runs deb task
//		debTask.getActions().forEach(action -> action.execute(debTask));
//
//		return debFile;

	}
	
	private Deb createDebTask() {
		return Context.getGradleContext().getProject().getTasks().create("createDeb_" + UUID.randomUUID(), Deb.class);
	}
	
}
