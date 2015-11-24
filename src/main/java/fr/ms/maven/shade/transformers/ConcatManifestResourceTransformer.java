/*
 * Copyright 2015 Marco Semiao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package fr.ms.maven.shade.transformers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.apache.maven.plugins.shade.relocation.Relocator;
import org.apache.maven.plugins.shade.resource.ManifestResourceTransformer;
import org.codehaus.plexus.util.IOUtil;

/**
 *
 * @see <a href="http://marcosemiao4j.wordpress.com">Marco4J</a>
 *
 *
 * @author Marco Semiao
 *
 */
public class ConcatManifestResourceTransformer extends ManifestResourceTransformer {

    // Configuration
    protected String mainClass;

    protected Map<String, Attributes> manifestEntries;

    protected Manifest manifest;

    @Override
    public void processResource(final String resource, final InputStream is, final List<Relocator> relocators) throws IOException {
	if (manifest == null) {
	    manifest = new Manifest(is);
	} else {
	    final Manifest currentManifest = new Manifest(is);
	    manifest.getMainAttributes().putAll(currentManifest.getMainAttributes());
	    manifest.getEntries().putAll(currentManifest.getEntries());
	}
	IOUtil.close(is);
    }

    @Override
    public void modifyOutputStream(final JarOutputStream jos) throws IOException {
	// If we didn't find a manifest, then let's create one.
	if (manifest == null) {
	    manifest = new Manifest();
	}

	final Attributes attributes = manifest.getMainAttributes();

	if (mainClass != null) {
	    attributes.put(Attributes.Name.MAIN_CLASS, mainClass);
	}

	if (manifestEntries != null) {
	    for (final Map.Entry<String, Attributes> entry : manifestEntries.entrySet()) {
		attributes.put(new Attributes.Name(entry.getKey()), entry.getValue());
	    }
	}

	jos.putNextEntry(new JarEntry(JarFile.MANIFEST_NAME));
	manifest.write(jos);
    }
}
