/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.kxml2.wap.WbxmlParser
 *  org.kxml2.wap.WbxmlSerializer
 *  org.kxml2.wap.syncml.SyncML
 */
package org.kxml2.wap.syncml;

import org.kxml2.wap.WbxmlParser;
import org.kxml2.wap.WbxmlSerializer;

/*
 * Exception performing whole class analysis ignored.
 */
public abstract class SyncML {
    public static final String[] TAG_TABLE_0 = new String[]{"Add", "Alert", "Archive", "Atomic", "Chal", "Cmd", "CmdID", "CmdRef", "Copy", "Cred", "Data", "Delete", "Exec", "Final", "Get", "Item", "Lang", "LocName", "LocURI", "Map", "MapItem", "Meta", "MsgID", "MsgRef", "NoResp", "NoResults", "Put", "Replace", "RespURI", "Results", "Search", "Sequence", "SessionID", "SftDel", "Source", "SourceRef", "Status", "Sync", "SyncBody", "SyncHdr", "SyncML", "Target", "TargetRef", "Reserved for future use", "VerDTD", "VerProto", "NumberOfChanged", "MoreData", "Field", "Filter", "Record", "FilterType", "SourceParent", "TargetParent", "Move", "Correlator"};
    public static final String[] TAG_TABLE_1 = new String[]{"Anchor", "EMI", "Format", "FreeID", "FreeMem", "Last", "Mark", "MaxMsgSize", "Mem", "MetInf", "Next", "NextNonce", "SharedMem", "Size", "Type", "Version", "MaxObjSize", "FieldLevel"};
    public static final String[] TAG_TABLE_2_DM = new String[]{"AccessType", "ACL", "Add", "b64", "bin", "bool", "chr", "CaseSense", "CIS", "Copy", "CS", "date", "DDFName", "DefaultValue", "Delete", "Description", "DDFFormat", "DFProperties", "DFTitle", "DFType", "Dynamic", "Exec", "float", "Format", "Get", "int", "Man", "MgmtTree", "MIME", "Mod", "Name", "Node", "node", "NodeName", "null", "Occurence", "One", "OneOrMore", "OneOrN", "Path", "Permanent", "Replace", "RTProperties", "Scope", "Size", "time", "Title", "TStamp", "Type", "Value", "VerDTD", "VerNo", "xml", "ZeroOrMore", "ZeroOrN", "ZeroOrOne"};

    public static WbxmlParser createParser() {
        WbxmlParser p = new WbxmlParser();
        p.setTagTable(0, TAG_TABLE_0);
        p.setTagTable(1, TAG_TABLE_1);
        return p;
    }

    public static WbxmlSerializer createSerializer() {
        WbxmlSerializer s = new WbxmlSerializer();
        s.setTagTable(0, TAG_TABLE_0);
        s.setTagTable(1, TAG_TABLE_1);
        return s;
    }

    public static WbxmlParser createDMParser() {
        WbxmlParser p = SyncML.createParser();
        p.setTagTable(2, TAG_TABLE_2_DM);
        return p;
    }

    public static WbxmlSerializer createDMSerializer() {
        WbxmlSerializer s = SyncML.createSerializer();
        s.setTagTable(2, TAG_TABLE_2_DM);
        return s;
    }
}

