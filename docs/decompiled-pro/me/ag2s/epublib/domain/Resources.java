/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.ag2s.epublib.domain.MediaType
 *  me.ag2s.epublib.domain.MediaTypes
 *  me.ag2s.epublib.domain.Resource
 *  me.ag2s.epublib.domain.Resources
 *  me.ag2s.epublib.util.StringUtil
 */
package me.ag2s.epublib.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.ag2s.epublib.domain.MediaType;
import me.ag2s.epublib.domain.MediaTypes;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.util.StringUtil;

/*
 * Exception performing whole class analysis ignored.
 */
public class Resources
implements Serializable {
    private static final long serialVersionUID = 2450876953383871451L;
    private static final String IMAGE_PREFIX = "image_";
    private static final String ITEM_PREFIX = "item_";
    private int lastId = 1;
    private Map<String, Resource> resources = new HashMap();

    public Resource add(Resource resource) {
        this.fixResourceHref(resource);
        this.fixResourceId(resource);
        this.resources.put(resource.getHref(), resource);
        return resource;
    }

    public void fixResourceId(Resource resource) {
        String resourceId = resource.getId();
        if (StringUtil.isBlank((String)resource.getId())) {
            resourceId = StringUtil.substringBeforeLast((String)resource.getHref(), (char)'.');
            resourceId = StringUtil.substringAfterLast((String)resourceId, (char)'/');
        }
        if (StringUtil.isBlank((String)(resourceId = this.makeValidId(resourceId, resource))) || this.containsId(resourceId)) {
            resourceId = this.createUniqueResourceId(resource);
        }
        resource.setId(resourceId);
    }

    private String makeValidId(String resourceId, Resource resource) {
        if (StringUtil.isNotBlank((String)resourceId) && !Character.isJavaIdentifierStart(resourceId.charAt(0))) {
            resourceId = this.getResourceItemPrefix(resource) + resourceId;
        }
        return resourceId;
    }

    private String getResourceItemPrefix(Resource resource) {
        String result2 = MediaTypes.isBitmapImage((MediaType)resource.getMediaType()) ? "image_" : "item_";
        return result2;
    }

    private String createUniqueResourceId(Resource resource) {
        int counter = this.lastId;
        if (counter == Integer.MAX_VALUE) {
            if (this.resources.size() == Integer.MAX_VALUE) {
                throw new IllegalArgumentException("Resources contains 2147483647 elements: no new elements can be added");
            }
            counter = 1;
        }
        String prefix = this.getResourceItemPrefix(resource);
        String result2 = prefix + counter;
        while (this.containsId(result2)) {
            result2 = prefix + ++counter;
        }
        this.lastId = counter;
        return result2;
    }

    public boolean containsId(String id) {
        if (StringUtil.isBlank((String)id)) {
            return false;
        }
        for (Resource resource : this.resources.values()) {
            if (!id.equals(resource.getId())) continue;
            return true;
        }
        return false;
    }

    public Resource getById(String id) {
        if (StringUtil.isBlank((String)id)) {
            return null;
        }
        for (Resource resource : this.resources.values()) {
            if (!id.equals(resource.getId())) continue;
            return resource;
        }
        return null;
    }

    public Resource getByProperties(String properties) {
        if (StringUtil.isBlank((String)properties)) {
            return null;
        }
        for (Resource resource : this.resources.values()) {
            if (!properties.equals(resource.getProperties())) continue;
            return resource;
        }
        return null;
    }

    public Resource remove(String href) {
        return (Resource)this.resources.remove(href);
    }

    private void fixResourceHref(Resource resource) {
        if (StringUtil.isNotBlank((String)resource.getHref()) && !this.resources.containsKey(resource.getHref())) {
            return;
        }
        if (StringUtil.isBlank((String)resource.getHref())) {
            if (resource.getMediaType() == null) {
                throw new IllegalArgumentException("Resource must have either a MediaType or a href");
            }
            int i = 1;
            String href = this.createHref(resource.getMediaType(), i);
            while (this.resources.containsKey(href)) {
                href = this.createHref(resource.getMediaType(), ++i);
            }
            resource.setHref(href);
        }
    }

    private String createHref(MediaType mediaType, int counter) {
        if (MediaTypes.isBitmapImage((MediaType)mediaType)) {
            return "image_" + counter + mediaType.getDefaultExtension();
        }
        return "item_" + counter + mediaType.getDefaultExtension();
    }

    public boolean isEmpty() {
        return this.resources.isEmpty();
    }

    public int size() {
        return this.resources.size();
    }

    public Map<String, Resource> getResourceMap() {
        return this.resources;
    }

    public Collection<Resource> getAll() {
        return this.resources.values();
    }

    public boolean notContainsByHref(String href) {
        if (StringUtil.isBlank((String)href)) {
            return true;
        }
        return !this.resources.containsKey(StringUtil.substringBefore((String)href, (char)'#'));
    }

    public boolean containsByHref(String href) {
        return !this.notContainsByHref(href);
    }

    public void set(Collection<Resource> resources) {
        this.resources.clear();
        this.addAll(resources);
    }

    public void addAll(Collection<Resource> resources) {
        for (Resource resource : resources) {
            this.fixResourceHref(resource);
            this.resources.put(resource.getHref(), resource);
        }
    }

    public void set(Map<String, Resource> resources) {
        this.resources = new HashMap<String, Resource>(resources);
    }

    public Resource getByIdOrHref(String idOrHref) {
        Resource resource = this.getById(idOrHref);
        if (resource == null) {
            resource = this.getByHref(idOrHref);
        }
        return resource;
    }

    public Resource getByHref(String href) {
        if (StringUtil.isBlank((String)href)) {
            return null;
        }
        href = StringUtil.substringBefore((String)href, (char)'#');
        return (Resource)this.resources.get(href);
    }

    public Resource findFirstResourceByMediaType(MediaType mediaType) {
        return Resources.findFirstResourceByMediaType(this.resources.values(), (MediaType)mediaType);
    }

    public static Resource findFirstResourceByMediaType(Collection<Resource> resources, MediaType mediaType) {
        for (Resource resource : resources) {
            if (resource.getMediaType() != mediaType) continue;
            return resource;
        }
        return null;
    }

    public List<Resource> getResourcesByMediaType(MediaType mediaType) {
        ArrayList<Resource> result2 = new ArrayList<Resource>();
        if (mediaType == null) {
            return result2;
        }
        for (Resource resource : this.getAll()) {
            if (resource.getMediaType() != mediaType) continue;
            result2.add(resource);
        }
        return result2;
    }

    public List<Resource> getResourcesByMediaTypes(MediaType[] mediaTypes) {
        ArrayList<Resource> result2 = new ArrayList<Resource>();
        if (mediaTypes == null) {
            return result2;
        }
        List<MediaType> mediaTypesList = Arrays.asList(mediaTypes);
        for (Resource resource : this.getAll()) {
            if (!mediaTypesList.contains(resource.getMediaType())) continue;
            result2.add(resource);
        }
        return result2;
    }

    public Collection<String> getAllHrefs() {
        return this.resources.keySet();
    }
}

