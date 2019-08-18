package de.hhu.stups.probplugin;

import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fabian on 20.11.18.
 */
public class Utils {

    private static final STGroup JAVA_GROUP = new STGroupFile(TraceGeneratorPlugin.class.getResource("JavaTemplate.stg"));

    private static final STGroup CPP_GROUP = new STGroupFile(TraceGeneratorPlugin.class.getResource("CppTemplate.stg"));

    private static final STGroup B_GROUP = new STGroupFile(TraceGeneratorPlugin.class.getResource("BTemplate.stg"));

    public static final Map<String, STGroup> TEMPLATE_MAP = new HashMap<>();

    static {
        TEMPLATE_MAP.put("Java", JAVA_GROUP);
        TEMPLATE_MAP.put("C++", CPP_GROUP);
        TEMPLATE_MAP.put("B", B_GROUP);
    }

    public static STGroup getGroup(String mode) {
        return TEMPLATE_MAP.get(mode);
    }
}
