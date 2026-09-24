/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.legado.app.model.analyzeRule.QueryTTF
 *  io.legado.app.model.analyzeRule.QueryTTF$ByteArrayReader
 *  io.legado.app.model.analyzeRule.QueryTTF$CmapFormat
 *  io.legado.app.model.analyzeRule.QueryTTF$CmapFormat12
 *  io.legado.app.model.analyzeRule.QueryTTF$CmapFormat4
 *  io.legado.app.model.analyzeRule.QueryTTF$CmapFormat6
 *  io.legado.app.model.analyzeRule.QueryTTF$CmapLayout
 *  io.legado.app.model.analyzeRule.QueryTTF$CmapRecord
 *  io.legado.app.model.analyzeRule.QueryTTF$Directory
 *  io.legado.app.model.analyzeRule.QueryTTF$GlyfLayout
 *  io.legado.app.model.analyzeRule.QueryTTF$HeadLayout
 *  io.legado.app.model.analyzeRule.QueryTTF$Header
 *  io.legado.app.model.analyzeRule.QueryTTF$MaxpLayout
 *  io.legado.app.model.analyzeRule.QueryTTF$NameLayout
 *  io.legado.app.model.analyzeRule.QueryTTF$NameRecord
 *  org.apache.commons.lang3.tuple.Pair
 *  org.apache.commons.lang3.tuple.Triple
 */
package io.legado.app.model.analyzeRule;

import io.legado.app.model.analyzeRule.QueryTTF;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

public class QueryTTF {
    private final ByteArrayReader fontReader;
    private final Header fileHeader = new Header(null);
    private final List<Directory> directorys = new LinkedList();
    private final NameLayout name = new NameLayout(null);
    private final HeadLayout head = new HeadLayout(null);
    private final MaxpLayout maxp = new MaxpLayout(null);
    private final List<Integer> loca = new LinkedList();
    private final CmapLayout Cmap = new CmapLayout(null);
    private final List<GlyfLayout> glyf = new LinkedList();
    private final Pair<Integer, Integer>[] pps = new Pair[]{Pair.of((Object)3, (Object)10), Pair.of((Object)0, (Object)4), Pair.of((Object)3, (Object)1), Pair.of((Object)1, (Object)0), Pair.of((Object)0, (Object)3), Pair.of((Object)0, (Object)1)};
    public final Map<Integer, String> codeToGlyph = new HashMap();
    public final Map<String, Integer> glyphToCode = new HashMap();
    private int limitMix = 0;
    private int limitMax = 0;

    public QueryTTF(byte[] buffer) {
        int i;
        this.fontReader = new ByteArrayReader(buffer, 0);
        this.fileHeader.majorVersion = this.fontReader.ReadUInt16();
        this.fileHeader.minorVersion = this.fontReader.ReadUInt16();
        this.fileHeader.numOfTables = this.fontReader.ReadUInt16();
        this.fileHeader.searchRange = this.fontReader.ReadUInt16();
        this.fileHeader.entrySelector = this.fontReader.ReadUInt16();
        this.fileHeader.rangeShift = this.fontReader.ReadUInt16();
        for (int i2 = 0; i2 < this.fileHeader.numOfTables; ++i2) {
            Directory d = new Directory(null);
            d.tag = this.fontReader.ReadStrings(4, StandardCharsets.US_ASCII);
            d.checkSum = this.fontReader.ReadUInt32();
            d.offset = this.fontReader.ReadUInt32();
            d.length = this.fontReader.ReadUInt32();
            this.directorys.add(d);
        }
        for (Directory Temp : this.directorys) {
            if (!Temp.tag.equals("name")) continue;
            this.fontReader.index = Temp.offset;
            this.name.format = this.fontReader.ReadUInt16();
            this.name.count = this.fontReader.ReadUInt16();
            this.name.stringOffset = this.fontReader.ReadUInt16();
            for (i = 0; i < this.name.count; ++i) {
                NameRecord record = new NameRecord(null);
                record.platformID = this.fontReader.ReadUInt16();
                record.encodingID = this.fontReader.ReadUInt16();
                record.languageID = this.fontReader.ReadUInt16();
                record.nameID = this.fontReader.ReadUInt16();
                record.length = this.fontReader.ReadUInt16();
                record.offset = this.fontReader.ReadUInt16();
                this.name.records.add(record);
            }
        }
        for (Directory Temp : this.directorys) {
            if (!Temp.tag.equals("head")) continue;
            this.fontReader.index = Temp.offset;
            this.head.majorVersion = this.fontReader.ReadUInt16();
            this.head.minorVersion = this.fontReader.ReadUInt16();
            this.head.fontRevision = this.fontReader.ReadUInt32();
            this.head.checkSumAdjustment = this.fontReader.ReadUInt32();
            this.head.magicNumber = this.fontReader.ReadUInt32();
            this.head.flags = this.fontReader.ReadUInt16();
            this.head.unitsPerEm = this.fontReader.ReadUInt16();
            this.head.created = this.fontReader.ReadUInt64();
            this.head.modified = this.fontReader.ReadUInt64();
            this.head.xMin = this.fontReader.ReadInt16();
            this.head.yMin = this.fontReader.ReadInt16();
            this.head.xMax = this.fontReader.ReadInt16();
            this.head.yMax = this.fontReader.ReadInt16();
            this.head.macStyle = this.fontReader.ReadUInt16();
            this.head.lowestRecPPEM = this.fontReader.ReadUInt16();
            this.head.fontDirectionHint = this.fontReader.ReadInt16();
            this.head.indexToLocFormat = this.fontReader.ReadInt16();
            this.head.glyphDataFormat = this.fontReader.ReadInt16();
        }
        for (Directory Temp : this.directorys) {
            if (!Temp.tag.equals("maxp")) continue;
            this.fontReader.index = Temp.offset;
            this.maxp.majorVersion = this.fontReader.ReadUInt16();
            this.maxp.minorVersion = this.fontReader.ReadUInt16();
            this.maxp.numGlyphs = this.fontReader.ReadUInt16();
            this.maxp.maxPoints = this.fontReader.ReadUInt16();
            this.maxp.maxContours = this.fontReader.ReadUInt16();
            this.maxp.maxCompositePoints = this.fontReader.ReadUInt16();
            this.maxp.maxCompositeContours = this.fontReader.ReadUInt16();
            this.maxp.maxZones = this.fontReader.ReadUInt16();
            this.maxp.maxTwilightPoints = this.fontReader.ReadUInt16();
            this.maxp.maxStorage = this.fontReader.ReadUInt16();
            this.maxp.maxFunctionDefs = this.fontReader.ReadUInt16();
            this.maxp.maxInstructionDefs = this.fontReader.ReadUInt16();
            this.maxp.maxStackElements = this.fontReader.ReadUInt16();
            this.maxp.maxSizeOfInstructions = this.fontReader.ReadUInt16();
            this.maxp.maxComponentElements = this.fontReader.ReadUInt16();
            this.maxp.maxComponentDepth = this.fontReader.ReadUInt16();
        }
        for (Directory Temp : this.directorys) {
            if (!Temp.tag.equals("loca")) continue;
            this.fontReader.index = Temp.offset;
            int offset = this.head.indexToLocFormat == 0 ? 2 : 4;
            for (long i3 = 0L; i3 < (long)Temp.length; i3 += (long)offset) {
                this.loca.add(offset == 2 ? this.fontReader.ReadUInt16() << 1 : this.fontReader.ReadUInt32());
            }
        }
        for (Directory Temp : this.directorys) {
            if (!Temp.tag.equals("cmap")) continue;
            this.fontReader.index = Temp.offset;
            this.Cmap.version = this.fontReader.ReadUInt16();
            this.Cmap.numTables = this.fontReader.ReadUInt16();
            for (i = 0; i < this.Cmap.numTables; ++i) {
                CmapRecord record = new CmapRecord(null);
                record.platformID = this.fontReader.ReadUInt16();
                record.encodingID = this.fontReader.ReadUInt16();
                record.offset = this.fontReader.ReadUInt32();
                this.Cmap.records.add(record);
            }
            for (i = 0; i < this.Cmap.numTables; ++i) {
                CmapFormat f;
                int fmtOffset = ((CmapRecord)this.Cmap.records.get((int)i)).offset;
                int EndIndex = this.fontReader.index = Temp.offset + fmtOffset;
                int format = this.fontReader.ReadUInt16();
                if (this.Cmap.tables.containsKey(fmtOffset)) continue;
                if (format == 0) {
                    f = new CmapFormat(null);
                    f.format = format;
                    f.length = this.fontReader.ReadUInt16();
                    f.language = this.fontReader.ReadUInt16();
                    f.glyphIdArray = this.fontReader.GetBytes(f.length - 6);
                    this.Cmap.tables.put(fmtOffset, f);
                    continue;
                }
                if (format == 4) {
                    f = new CmapFormat4(null);
                    f.format = format;
                    f.length = this.fontReader.ReadUInt16();
                    f.language = this.fontReader.ReadUInt16();
                    f.segCountX2 = this.fontReader.ReadUInt16();
                    int segCount = f.segCountX2 >> 1;
                    f.searchRange = this.fontReader.ReadUInt16();
                    f.entrySelector = this.fontReader.ReadUInt16();
                    f.rangeShift = this.fontReader.ReadUInt16();
                    f.endCode = this.fontReader.GetUInt16Array(segCount);
                    f.reservedPad = this.fontReader.ReadUInt16();
                    f.startCode = this.fontReader.GetUInt16Array(segCount);
                    f.idDelta = this.fontReader.GetInt16Array(segCount);
                    f.idRangeOffset = this.fontReader.GetUInt16Array(segCount);
                    f.glyphIdArray = this.fontReader.GetUInt16Array(EndIndex + f.length - this.fontReader.index >> 1);
                    this.Cmap.tables.put(fmtOffset, f);
                    continue;
                }
                if (format == 6) {
                    f = new CmapFormat6(null);
                    f.format = format;
                    f.length = this.fontReader.ReadUInt16();
                    f.language = this.fontReader.ReadUInt16();
                    f.firstCode = this.fontReader.ReadUInt16();
                    f.entryCount = this.fontReader.ReadUInt16();
                    f.glyphIdArray = this.fontReader.GetUInt16Array(f.entryCount);
                    this.Cmap.tables.put(fmtOffset, f);
                    continue;
                }
                if (format != 12) continue;
                f = new CmapFormat12(null);
                f.format = format;
                f.reserved = this.fontReader.ReadUInt16();
                f.length = this.fontReader.ReadUInt32();
                f.language = this.fontReader.ReadUInt32();
                f.numGroups = this.fontReader.ReadUInt32();
                f.groups = new ArrayList(f.numGroups);
                for (int n = 0; n < f.numGroups; ++n) {
                    f.groups.add(Triple.of((Object)this.fontReader.ReadUInt32(), (Object)this.fontReader.ReadUInt32(), (Object)this.fontReader.ReadUInt32()));
                }
                this.Cmap.tables.put(fmtOffset, f);
            }
        }
        for (Directory Temp : this.directorys) {
            if (!Temp.tag.equals("glyf")) continue;
            this.fontReader.index = Temp.offset;
            for (i = 0; i < this.maxp.numGlyphs; ++i) {
                short same;
                int n;
                this.fontReader.index = Temp.offset + (Integer)this.loca.get(i);
                short numberOfContours = this.fontReader.ReadInt16();
                if (numberOfContours <= 0) continue;
                GlyfLayout g = new GlyfLayout(null);
                g.numberOfContours = numberOfContours;
                g.xMin = this.fontReader.ReadInt16();
                g.yMin = this.fontReader.ReadInt16();
                g.xMax = this.fontReader.ReadInt16();
                g.yMax = this.fontReader.ReadInt16();
                g.endPtsOfContours = this.fontReader.GetUInt16Array((int)numberOfContours);
                g.instructionLength = this.fontReader.ReadUInt16();
                g.instructions = this.fontReader.GetBytes(g.instructionLength);
                int flagLength = g.endPtsOfContours[g.endPtsOfContours.length - 1] + 1;
                g.flags = new byte[flagLength];
                for (n = 0; n < flagLength; ++n) {
                    g.flags[n] = this.fontReader.GetByte();
                    if ((g.flags[n] & 8) == 0) continue;
                    for (int m = this.fontReader.ReadUInt8(); m > 0; --m) {
                        g.flags[++n] = g.flags[n - 1];
                    }
                }
                g.xCoordinates = new short[flagLength];
                for (n = 0; n < flagLength; ++n) {
                    same = (short)((g.flags[n] & 0x10) != 0 ? 1 : -1);
                    g.xCoordinates[n] = (g.flags[n] & 2) != 0 ? (short)(same * this.fontReader.ReadUInt8()) : (same == 1 ? (short)0 : this.fontReader.ReadInt16());
                }
                g.yCoordinates = new short[flagLength];
                for (n = 0; n < flagLength; ++n) {
                    same = (short)((g.flags[n] & 0x20) != 0 ? 1 : -1);
                    g.yCoordinates[n] = (g.flags[n] & 4) != 0 ? (short)(same * this.fontReader.ReadUInt8()) : (same == 1 ? (short)0 : this.fontReader.ReadInt16());
                }
                this.glyf.add(g);
            }
        }
        for (int key = 0; key < 130000; ++key) {
            int gid;
            if (key == 255) {
                key = 13312;
            }
            if ((gid = this.getGlyfIndex(key)) == 0) continue;
            StringBuilder sb = new StringBuilder();
            for (short b : ((GlyfLayout)this.glyf.get((int)gid)).xCoordinates) {
                sb.append(b);
            }
            for (short b : ((GlyfLayout)this.glyf.get((int)gid)).yCoordinates) {
                sb.append(b);
            }
            String val = sb.toString();
            if (this.limitMix == 0) {
                this.limitMix = key;
            }
            this.limitMax = key;
            this.codeToGlyph.put(key, val);
            if (this.glyphToCode.containsKey(val)) continue;
            this.glyphToCode.put(val, key);
        }
    }

    public String getNameById(int nameId) {
        for (Directory Temp : this.directorys) {
            if (!Temp.tag.equals("name")) continue;
            this.fontReader.index = Temp.offset;
            break;
        }
        for (NameRecord record : this.name.records) {
            if (record.nameID != nameId) continue;
            this.fontReader.index += this.name.stringOffset + record.offset;
            return this.fontReader.ReadStrings(record.length, record.platformID == 1 ? StandardCharsets.UTF_8 : StandardCharsets.UTF_16BE);
        }
        return "error";
    }

    private int getGlyfIndex(int code) {
        CmapFormat4 tab;
        if (code == 0) {
            return 0;
        }
        int fmtKey = 0;
        for (Pair item : this.pps) {
            for (CmapRecord record : this.Cmap.records) {
                if ((Integer)item.getLeft() != record.platformID || (Integer)item.getRight() != record.encodingID) continue;
                fmtKey = record.offset;
                break;
            }
            if (fmtKey > 0) break;
        }
        if (fmtKey == 0) {
            return 0;
        }
        int glyfID = 0;
        CmapFormat table = (CmapFormat)this.Cmap.tables.get(fmtKey);
        assert (table != null);
        int fmt = table.format;
        if (fmt == 0) {
            if (code < table.glyphIdArray.length) {
                glyfID = table.glyphIdArray[code] & 0xFF;
            }
        } else if (fmt == 4) {
            tab = (CmapFormat4)table;
            if (code > tab.endCode[tab.endCode.length - 1]) {
                return 0;
            }
            int start2 = 0;
            int end = tab.endCode.length - 1;
            while (start2 + 1 < end) {
                int middle = (start2 + end) / 2;
                if (tab.endCode[middle] <= code) {
                    start2 = middle;
                    continue;
                }
                end = middle;
            }
            if (tab.endCode[start2] < code) {
                ++start2;
            }
            if (code < tab.startCode[start2]) {
                return 0;
            }
            glyfID = tab.idRangeOffset[start2] != 0 ? tab.glyphIdArray[code - tab.startCode[start2] + (tab.idRangeOffset[start2] >> 1) - (tab.idRangeOffset.length - start2)] : code + tab.idDelta[start2];
            glyfID &= 0xFFFF;
        } else if (fmt == 6) {
            tab = (CmapFormat6)table;
            int index = code - tab.firstCode;
            glyfID = index < 0 || index >= tab.glyphIdArray.length ? 0 : tab.glyphIdArray[index];
        } else if (fmt == 12) {
            tab = (CmapFormat12)table;
            if (code > (Integer)((Triple)tab.groups.get(tab.numGroups - 1)).getMiddle()) {
                return 0;
            }
            int start3 = 0;
            int end = tab.numGroups - 1;
            while (start3 + 1 < end) {
                int middle = (start3 + end) / 2;
                if ((Integer)((Triple)tab.groups.get(middle)).getLeft() <= code) {
                    start3 = middle;
                    continue;
                }
                end = middle;
            }
            if ((Integer)((Triple)tab.groups.get(start3)).getLeft() <= code && code <= (Integer)((Triple)tab.groups.get(start3)).getMiddle()) {
                glyfID = (Integer)((Triple)tab.groups.get(start3)).getRight() + code - (Integer)((Triple)tab.groups.get(start3)).getLeft();
            }
        }
        return glyfID;
    }

    public boolean inLimit(char code) {
        return this.limitMix <= code && code < this.limitMax;
    }

    public String getGlyfByCode(int key) {
        return this.codeToGlyph.getOrDefault(key, "");
    }

    public int getCodeByGlyf(String val) {
        return this.glyphToCode.getOrDefault(val, 0);
    }
}

