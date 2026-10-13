/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.loader.jar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.loader.data.RandomAccessData;
import org.springframework.boot.loader.jar.CentralDirectoryEndRecord;
import org.springframework.boot.loader.jar.CentralDirectoryFileHeader;
import org.springframework.boot.loader.jar.CentralDirectoryVisitor;

class CentralDirectoryParser {
    private static final int CENTRAL_DIRECTORY_HEADER_BASE_SIZE = 46;
    private final List<CentralDirectoryVisitor> visitors = new ArrayList<CentralDirectoryVisitor>();

    CentralDirectoryParser() {
    }

    public <T extends CentralDirectoryVisitor> T addVisitor(T visitor) {
        this.visitors.add(visitor);
        return visitor;
    }

    public RandomAccessData parse(RandomAccessData data, boolean skipPrefixBytes) throws IOException {
        CentralDirectoryEndRecord endRecord = new CentralDirectoryEndRecord(data);
        if (skipPrefixBytes) {
            data = this.getArchiveData(endRecord, data);
        }
        RandomAccessData centralDirectoryData = endRecord.getCentralDirectory(data);
        this.visitStart(endRecord, centralDirectoryData);
        this.parseEntries(endRecord, centralDirectoryData);
        this.visitEnd();
        return data;
    }

    private void parseEntries(CentralDirectoryEndRecord endRecord, RandomAccessData centralDirectoryData) throws IOException {
        byte[] bytes2 = centralDirectoryData.read(0L, centralDirectoryData.getSize());
        CentralDirectoryFileHeader fileHeader = new CentralDirectoryFileHeader();
        int dataOffset = 0;
        for (int i = 0; i < endRecord.getNumberOfRecords(); ++i) {
            fileHeader.load(bytes2, dataOffset, null, 0, null);
            this.visitFileHeader(dataOffset, fileHeader);
            dataOffset += 46 + fileHeader.getName().length() + fileHeader.getComment().length() + fileHeader.getExtra().length;
        }
    }

    private RandomAccessData getArchiveData(CentralDirectoryEndRecord endRecord, RandomAccessData data) {
        long offset = endRecord.getStartOfArchive(data);
        if (offset == 0L) {
            return data;
        }
        return data.getSubsection(offset, data.getSize() - offset);
    }

    private void visitStart(CentralDirectoryEndRecord endRecord, RandomAccessData centralDirectoryData) {
        for (CentralDirectoryVisitor visitor : this.visitors) {
            visitor.visitStart(endRecord, centralDirectoryData);
        }
    }

    private void visitFileHeader(int dataOffset, CentralDirectoryFileHeader fileHeader) {
        for (CentralDirectoryVisitor visitor : this.visitors) {
            visitor.visitFileHeader(fileHeader, dataOffset);
        }
    }

    private void visitEnd() {
        for (CentralDirectoryVisitor visitor : this.visitors) {
            visitor.visitEnd();
        }
    }
}

