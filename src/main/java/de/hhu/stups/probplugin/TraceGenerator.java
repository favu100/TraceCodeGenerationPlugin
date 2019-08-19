package de.hhu.stups.probplugin;

import com.google.inject.Singleton;
import de.prob.statespace.Transition;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fabian on 10.11.18.
 */

@Singleton
public class TraceGenerator {

    public String generateAllOperations(String machine, String clazz, String includedMachine, STGroup group, List<Transition> transitions) {
        ST template = group.getInstanceOf("main");
        List<String> statements = transitions.stream()
                .map(t -> generateOperation(includedMachine, t.getName(), t.getParameterValues(), group))
                .collect(Collectors.toList());
        TemplateHandler.add(template, "machine", machine);
        TemplateHandler.add(template, "includedMachine", includedMachine);
        TemplateHandler.add(template,"initialization", generateInitialization(group, clazz, includedMachine));
        TemplateHandler.add(template,"simulation", statements);
        return template.render();
    }

    private String generateInitialization(STGroup group, String clazz, String machine) {
        ST template = group.getInstanceOf("initialization");
        TemplateHandler.add(template,"class", clazz);
        TemplateHandler.add(template,"machine", machine);
        return template.render();
    }

    private String generateOperation(String includedMachine, String operation, List<String> params, STGroup group) {
        ST template = group.getInstanceOf("operation");
        TemplateHandler.add(template,"machine", includedMachine);
        if(!"$initialise_machine".equals(operation)) {
            TemplateHandler.add(template,"operation", operation);
        } else {
            return "";
        }
        TemplateHandler.add(template,"empty", params.isEmpty());
        TemplateHandler.add(template,"params", params);
        return template.render();
    }
}
