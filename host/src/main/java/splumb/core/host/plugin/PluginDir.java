package splumb.core.host.plugin;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.Files;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Set;

class PluginDir {
    private File dir = new File(System.getProperty("user.home"), ".splumb");
    private String parentDir;

    public PluginDir() {
        File pluginDir = new File(System.getProperty("user.home"), ".splumb/sample.jar");
        parentDir = pluginDir.getParent();

        try {
            Files.createParentDirs(pluginDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<File> getJarFiles() {

        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return Files.getFileExtension(s).equals("jar");
            }
        });

        return files == null ? ImmutableSet.<File>of() : ImmutableSet.copyOf(files);
    }

    public String path() {
        return parentDir;
    }
}
