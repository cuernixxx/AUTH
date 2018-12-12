package com.isb.sgs.auth.git;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.ArchiveCommand;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;

public class ZipFormat implements ArchiveCommand.Format {

	Logger _logger = Logger.getLogger(this.getClass());

	@Override
	public ArchiveOutputStream createArchiveOutputStream(OutputStream s) {
		return new ZipArchiveOutputStream(s);
	}

	public void putEntry(ArchiveOutputStream out, String path, FileMode mode, ObjectLoader loader) //
			throws IOException {
		final ZipArchiveEntry entry = new ZipArchiveEntry(path);
		// _logger.debug ("Incluyendo Path:" + path);
		if (mode == FileMode.REGULAR_FILE) {
			// ok
		} else if (mode == FileMode.EXECUTABLE_FILE || mode == FileMode.SYMLINK) {
			entry.setUnixMode(mode.getBits());
		} else {
			warnArchiveEntryModeIgnored(path);
		}
		if (loader != null) {
			entry.setSize(loader.getSize());
			out.putArchiveEntry(entry);
			loader.copyTo(out);
			out.closeArchiveEntry();
		}

	}

	@Override
	public Iterable suffixes() {
		// TODO Auto-generated method stub
		return null;
	}

	private static void warnArchiveEntryModeIgnored(String name) {
		;
	}

	@Override
	public void putEntry(Closeable out, String path, FileMode mode, ObjectLoader loader) throws IOException {
		this.putEntry((ArchiveOutputStream) out, path, mode, loader);

	}

	@Override
	public Closeable createArchiveOutputStream(OutputStream arg0, Map arg1) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putEntry(Closeable out, ObjectId idObject, String path, FileMode mode, ObjectLoader loader)
			throws IOException {
		this.putEntry((ArchiveOutputStream) out, path, mode, loader);

	}

}