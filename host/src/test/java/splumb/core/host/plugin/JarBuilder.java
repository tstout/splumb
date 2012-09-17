package splumb.core.host.plugin;

import java.io.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarBuilder {

    private JarOutputStream target;
    private Manifest manifest = new Manifest();
    private String basePath;

    public JarBuilder() {
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
    }

    public JarBuilder withBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }

    public JarBuilder withJarName(String jarName) {
        try {
            target = new JarOutputStream(new FileOutputStream(jarName), manifest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return this;
    }

    public void build() {
        try {
            target.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JarBuilder withFile(File source) {

        BufferedInputStream in = null;
        try {
            if (source.isDirectory()) {
                String name = source.getPath().replace("\\", "/");
                if (!name.isEmpty()) {
                    if (!name.endsWith("/"))
                        name += "/";


                    JarEntry entry = new JarEntry(name.replace(basePath, ""));

                    entry.setTime(source.lastModified());
                    target.putNextEntry(entry);
                    target.closeEntry();
                }

                for (File nestedFile : source.listFiles()) {
                    withFile(nestedFile);
                }

                return this;
            }

            JarEntry entry = new JarEntry(
                    source.getPath().replace("\\", "/").replace(basePath, ""));

            entry.setTime(source.lastModified());
            target.putNextEntry(entry);
            in = new BufferedInputStream(new FileInputStream(source));

            byte[] buffer = new byte[1024];
            while (true) {
                int count = in.read(buffer);
                if (count == -1)
                    break;
                target.write(buffer, 0, count);
            }
            target.closeEntry();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null)

                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }

        return this;
    }
}
