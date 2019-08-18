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

    public String generateAllOperations(String machine, String includedMachine, STGroup group, List<Transition> transitions) {
        ST template = group.getInstanceOf("main");
        List<String> statements = transitions.stream()
                .map(t -> generateOperation(includedMachine, t.getName(), t.getParameterValues(), group))
                .collect(Collectors.toList());
        template.add("machine", machine);
        template.add("includedMachine", includedMachine);
        template.add("simulation", statements);
        return template.render();
    }

    private String generateOperation(String includedMachine, String operation, List<String> params, STGroup group) {
        ST template = group.getInstanceOf("operation");
        template.add("machine", includedMachine);
        if(!"$initialise_machine".equals(operation)) {
            template.add("operation", operation);
        } else {
            return "";
        }
        template.add("empty", params.isEmpty());
        template.add("params", params);
        return template.render();
    }
}
