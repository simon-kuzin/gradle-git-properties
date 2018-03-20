package com.gorylenko

import java.io.File
import java.util.Map

class PropertiesFileWriter {

    boolean write(Map<String, String> properties, File file, boolean force) {
        if (!force && hasSameContent(file, properties)) {
            // Skipping writing [${file}] as it is up-to-date.
            return false
        } else {
            // Writing to [${file}]...
            writeToPropertiesFile(properties, file)
            return true
        }
    }


    private void writeToPropertiesFile(Map<String, String> properties, File propsFile) {
        if (!propsFile.parentFile.exists()) {
            propsFile.parentFile.mkdirs()
        }
        if (propsFile.exists()) {
            propsFile.delete()
        }
        propsFile.createNewFile()
        propsFile.withOutputStream {
            def props = new Properties()
            props.putAll(properties)
            props.store(it, null)
        }
    }

    private boolean hasSameContent(File propsFile, Map<String, String> properties) {
        boolean sameContent = false
        if (propsFile.exists()) {
            def props = new Properties()
            propsFile.withInputStream {
                props.load it
            }
            if (props.equals(properties)) {
                sameContent = true
            }
        }
        return sameContent
    }
}
