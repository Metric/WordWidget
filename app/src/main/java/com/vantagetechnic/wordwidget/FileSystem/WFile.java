package com.vantagetechnic.wordwidget.FileSystem;

import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.Metadata;

import java.io.File;

/**
 * Created by aaronklick on 8/12/17.
 */

public class WFile implements Comparable<WFile> {
    public File source;
    public Metadata entry;

    public WFile(File file) {
        source = file;
    }

    public WFile(Metadata meta) {
        entry = meta;
    }

    @Override
    public int compareTo(WFile f) {
        if(f.source != null && this.source != null) {
            if(f.source.isDirectory() && this.source.isFile()) {
                return 1;
            }
            else if(f.source.isFile() && this.source.isDirectory()) {
                return -1;
            }
            else {
                return f.source.getName().compareTo(this.source.getName());
            }
        }
        else if(f.entry != null && this.entry != null) {
            if(f.entry instanceof FolderMetadata && this.entry instanceof FileMetadata) {
                return 1;
            }
            else if(f.entry instanceof FileMetadata && this.entry instanceof FolderMetadata) {
                return -1;
            }
            else {
                return f.entry.getName().compareTo(this.entry.getName());
            }
        }

        return -1;
    }

    public String getParent() {
        if(this.source != null) {
            return this.source.getParent();
        }
        else if(this.entry != null) {
            return new File(this.entry.getPathLower()).getParent();
        }

        return "/";
    }

    public String getPath() {
        if(this.source != null) {
            return this.source.getAbsolutePath();
        }
        else if(this.entry != null) {
            return this.entry.getPathLower();
        }

        return "/";
    }

    public boolean isDirectory() {
        if(this.source != null) {
            return this.source.isDirectory();
        }
        else if(this.entry != null) {
            return this.entry instanceof FolderMetadata;
        }

        return false;
    }

    public String getName() {
        if(this.source != null) {
            return this.source.getName();
        }
        else if (this.entry != null) {
            return this.entry.getName();
        }

        return "/";
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if(o == this) return true;
        if(o instanceof WFile) {
            return o.hashCode() == this.hashCode();
        }

        return false;
    }

    @Override
    public int hashCode() {
        if(source != null) {
           return source.hashCode();
        }
        else if(entry != null) {
            return entry.hashCode();
        }

        return 0;
    }
}
