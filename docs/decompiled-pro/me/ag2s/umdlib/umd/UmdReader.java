/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.umdlib.domain.UmdBook
 *  me.ag2s.umdlib.domain.UmdCover
 *  me.ag2s.umdlib.domain.UmdHeader
 *  me.ag2s.umdlib.tool.StreamReader
 *  me.ag2s.umdlib.tool.UmdUtils
 *  me.ag2s.umdlib.umd.UmdReader
 */
package me.ag2s.umdlib.umd;

import java.io.IOException;
import java.io.InputStream;
import me.ag2s.umdlib.domain.UmdBook;
import me.ag2s.umdlib.domain.UmdCover;
import me.ag2s.umdlib.domain.UmdHeader;
import me.ag2s.umdlib.tool.StreamReader;
import me.ag2s.umdlib.tool.UmdUtils;

public class UmdReader {
    UmdBook book;
    InputStream inputStream;
    int _AdditionalCheckNumber;
    int _TotalContentLen;
    boolean end = false;

    public synchronized UmdBook read(InputStream inputStream) throws Exception {
        this.book = new UmdBook();
        this.inputStream = inputStream;
        StreamReader reader = new StreamReader(inputStream);
        UmdHeader umdHeader = new UmdHeader();
        this.book.setHeader(umdHeader);
        if (reader.readIntLe() != -560292983) {
            throw new IOException("Wrong header");
        }
        short num1 = -1;
        byte ch = reader.readByte();
        while (ch == 35) {
            short segType = reader.readShortLe();
            byte segFlag = reader.readByte();
            short len = (short)(reader.readUint8() - 5);
            System.out.println("\u5757\u6807\u8bc6:" + segType);
            this.ReadSection(segType, segFlag, len, reader, umdHeader);
            if (segType == 241 || segType == 10) {
                segType = num1;
            }
            ch = reader.readByte();
            while (ch == 36) {
                System.out.println(ch);
                int additionalCheckNumber = reader.readIntLe();
                int length2 = reader.readIntLe() - 9;
                this.ReadAdditionalSection(segType, additionalCheckNumber, length2, reader);
                ch = reader.readByte();
            }
            num1 = segType;
        }
        System.out.println(this.book.getHeader().toString());
        return this.book;
    }

    private void ReadAdditionalSection(short segType, int additionalCheckNumber, int length, StreamReader reader) throws Exception {
        switch (segType) {
            case 14: {
                break;
            }
            case 15: {
                break;
            }
            case 129: {
                reader.readBytes(length);
                break;
            }
            case 130: {
                this.book.setCover(new UmdCover(reader.readBytes(length)));
                break;
            }
            case 131: {
                System.out.println(length / 4);
                this.book.setNum(length / 4);
                for (int i = 0; i < length / 4; ++i) {
                    this.book.getChapters().addContentLength(Integer.valueOf(reader.readIntLe()));
                }
                break;
            }
            case 132: {
                System.out.println(this._AdditionalCheckNumber);
                System.out.println(additionalCheckNumber);
                if (this._AdditionalCheckNumber != additionalCheckNumber) {
                    System.out.println(length);
                    this.book.getChapters().contents.write(UmdUtils.decompress((byte[])reader.readBytes(length)));
                    this.book.getChapters().contents.flush();
                    break;
                }
                for (int i = 0; i < this.book.getNum(); ++i) {
                    short len = reader.readUint8();
                    byte[] title = reader.readBytes((int)len);
                    this.book.getChapters().addTitle(title);
                }
                break;
            }
        }
    }

    public void ReadSection(short segType, byte segFlag, short length, StreamReader reader, UmdHeader header) throws IOException {
        switch (segType) {
            case 1: {
                header.setUmdType(reader.readByte());
                reader.readBytes(2);
                System.out.println("UMD\u6587\u4ef6\u7c7b\u578b:" + header.getUmdType());
                break;
            }
            case 2: {
                header.setTitle(UmdUtils.unicodeBytesToString((byte[])reader.readBytes((int)length)));
                System.out.println("\u6587\u4ef6\u6807\u9898:" + header.getTitle());
                break;
            }
            case 3: {
                header.setAuthor(UmdUtils.unicodeBytesToString((byte[])reader.readBytes((int)length)));
                System.out.println("\u4f5c\u8005:" + header.getAuthor());
                break;
            }
            case 4: {
                header.setYear(UmdUtils.unicodeBytesToString((byte[])reader.readBytes((int)length)));
                System.out.println("\u5e74:" + header.getYear());
                break;
            }
            case 5: {
                header.setMonth(UmdUtils.unicodeBytesToString((byte[])reader.readBytes((int)length)));
                System.out.println("\u6708:" + header.getMonth());
                break;
            }
            case 6: {
                header.setDay(UmdUtils.unicodeBytesToString((byte[])reader.readBytes((int)length)));
                System.out.println("\u65e5:" + header.getDay());
                break;
            }
            case 7: {
                header.setBookType(UmdUtils.unicodeBytesToString((byte[])reader.readBytes((int)length)));
                System.out.println("\u5c0f\u8bf4\u7c7b\u578b:" + header.getBookType());
                break;
            }
            case 8: {
                header.setBookMan(UmdUtils.unicodeBytesToString((byte[])reader.readBytes((int)length)));
                System.out.println("\u51fa\u7248\u5546:" + header.getBookMan());
                break;
            }
            case 9: {
                header.setShopKeeper(UmdUtils.unicodeBytesToString((byte[])reader.readBytes((int)length)));
                System.out.println("\u96f6\u552e\u5546:" + header.getShopKeeper());
                break;
            }
            case 10: {
                System.out.println("CONTENT ID:" + reader.readHex((int)length));
                break;
            }
            case 11: {
                this._TotalContentLen = reader.readIntLe();
                this.book.getChapters().setTotalContentLen(this._TotalContentLen);
                System.out.println("\u5185\u5bb9\u957f\u5ea6:" + this._TotalContentLen);
                break;
            }
            case 12: {
                this.end = true;
                int num2 = reader.readIntLe();
                System.out.println("\u6574\u4e2a\u6587\u4ef6\u957f\u5ea6" + num2);
                break;
            }
            case 13: {
                break;
            }
            case 14: {
                byte num3 = reader.readByte();
                break;
            }
            case 15: {
                reader.readBytes((int)length);
                break;
            }
            case 129: 
            case 131: {
                this._AdditionalCheckNumber = reader.readIntLe();
                System.out.println("\u7ae0\u8282\u504f\u79fb:" + this._AdditionalCheckNumber);
                break;
            }
            case 132: {
                this._AdditionalCheckNumber = reader.readIntLe();
                System.out.println("\u7ae0\u8282\u6807\u9898\uff0c\u6b63\u6587:" + this._AdditionalCheckNumber);
                break;
            }
            case 130: {
                byte num4 = reader.readByte();
                this._AdditionalCheckNumber = reader.readIntLe();
                break;
            }
            case 135: {
                reader.readUint8();
                reader.readUint8();
                reader.readBytes(4);
                break;
            }
            case 240: {
                break;
            }
            case 241: {
                System.out.println("\u8bb8\u53ef\u8bc1(LICENCE KEY):" + reader.readHex(16));
                break;
            }
            default: {
                if (length <= 0) break;
                byte[] byArray = reader.readBytes((int)length);
            }
        }
    }

    public String toString() {
        return "UmdReader{book=" + this.book + '}';
    }
}

