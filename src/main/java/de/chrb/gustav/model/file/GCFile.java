package de.chrb.gustav.model.file;

import java.io.File;
import java.util.Objects;

import javax.annotation.concurrent.Immutable;

/**
 * Wrapper class for a gc file.
 *
 * @author Christian Bannes
 */
@Immutable
public class GCFile {
	private File file;

	/**
	 * Create a new gc file
	 *
	 * @param file the underlying gc file
	 */
	public GCFile(final File file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "file: " + this.file.getName();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.file);
	}

	@Override
	public boolean equals(final Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof GCFile)) return false;
		final GCFile that = (GCFile)obj;
		return Objects.equals(this.file, that.file);
	}

	/**
	 * The name of this gc file
	 * @return the name
	 */
	public String getName() {
		return this.toString();
	}

	/**
	 * The underlying file. Note, although the physical file is not immutable
	 * the java object itself is ummutable.
	 *
	 * @return the underlying file
	 */
	public File toFile() {
		return this.file;
	}


}
