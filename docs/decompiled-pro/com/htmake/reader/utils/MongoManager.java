/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.htmake.reader.entity.MongoFile
 *  com.htmake.reader.utils.ExtKt
 *  com.htmake.reader.utils.MongoManager
 *  com.mongodb.MongoClientSettings
 *  com.mongodb.MongoException
 *  com.mongodb.client.MongoClient
 *  com.mongodb.client.MongoClients
 *  com.mongodb.client.MongoCollection
 *  com.mongodb.client.MongoDatabase
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.bson.codecs.configuration.CodecProvider
 *  org.bson.codecs.configuration.CodecRegistries
 *  org.bson.codecs.configuration.CodecRegistry
 *  org.bson.codecs.pojo.PojoCodecProvider
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.htmake.reader.utils;

import com.htmake.reader.entity.MongoFile;
import com.htmake.reader.utils.ExtKt;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\t\u001a\u00020\bJ\u001e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bJ\u0006\u0010\u000f\u001a\u00020\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lcom/htmake/reader/utils/MongoManager;", "", "()V", "mongoClient", "Lcom/mongodb/client/MongoClient;", "connect", "", "uri", "", "db", "Lcom/mongodb/client/MongoDatabase;", "fileStorage", "Lcom/mongodb/client/MongoCollection;", "Lcom/htmake/reader/entity/MongoFile;", "collection", "isInit", "", "reader-pro"})
public final class MongoManager {
    @NotNull
    public static final MongoManager INSTANCE = new MongoManager();
    private static MongoClient mongoClient;

    private MongoManager() {
    }

    public final boolean isInit() {
        return mongoClient != null;
    }

    public final void connect(@NotNull String uri) {
        Intrinsics.checkNotNullParameter((Object)uri, (String)"uri");
        try {
            MongoClient mongoClient = MongoClients.create((String)uri);
            Intrinsics.checkNotNullExpressionValue((Object)mongoClient, (String)"create(uri)");
            MongoManager.mongoClient = mongoClient;
        }
        catch (MongoException e) {
            ExtKt.getLogger().info("mongodb \u8fde\u63a5\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u94fe\u63a5({})\u662f\u5426\u6b63\u786e", (Object)uri);
            e.printStackTrace();
        }
    }

    @Nullable
    public final MongoDatabase db(@NotNull String db) {
        Intrinsics.checkNotNullParameter((Object)db, (String)"db");
        if (!this.isInit()) {
            return null;
        }
        PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        MongoClient mongoClient = new CodecRegistry[2];
        mongoClient[0] = MongoClientSettings.getDefaultCodecRegistry();
        CodecProvider[] codecProviderArray = new CodecProvider[]{(CodecProvider)pojoCodecProvider};
        mongoClient[1] = CodecRegistries.fromProviders((CodecProvider[])codecProviderArray);
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries((CodecRegistry[])mongoClient);
        mongoClient = MongoManager.mongoClient;
        if (mongoClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"mongoClient");
            throw null;
        }
        return mongoClient.getDatabase(db).withCodecRegistry(pojoCodecRegistry);
    }

    @Nullable
    public final MongoCollection<MongoFile> fileStorage(@NotNull String db, @NotNull String collection) {
        Intrinsics.checkNotNullParameter((Object)db, (String)"db");
        Intrinsics.checkNotNullParameter((Object)collection, (String)"collection");
        MongoDatabase mongoDatabase = this.db(db);
        return mongoDatabase == null ? null : mongoDatabase.getCollection(collection, MongoFile.class);
    }
}

