package de.hhu.stups.probplugin;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.prob.statespace.Trace;
import de.prob2.ui.internal.StageManager;
import de.prob2.ui.prob2fx.CurrentTrace;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by fabian on 20.11.18.
 */

@Singleton
public class TraceGeneratorStage extends Stage {

    @FXML
    private ChoiceBox<String> languageChoice;

    @FXML
    private HBox machineBox;

    @FXML
    private TextField tfMachine;

    @FXML
    private TextField tfIncludedMachine;

    @FXML
    private Button btGenerate;

    @FXML
    private TextArea taGeneration;

    private final CurrentTrace currentTrace;

    private final TraceGenerator traceGenerator;

    @Inject
    private TraceGeneratorStage(final StageManager stageManager, final CurrentTrace currentTrace,
                                final TraceGenerator traceGenerator) {
        super();
        this.currentTrace = currentTrace;
        this.traceGenerator = traceGenerator;
        stageManager.loadFXML(this, "trace_generator_view.fxml");
    }

    @FXML
    private void initialize() {
        btGenerate.disableProperty().bind(currentTrace.existsProperty().not());
        languageChoice.valueProperty().addListener((observable, from, to) -> {
            if("B".equals(to)) {
                machineBox.setVisible(true);
            } else {
                machineBox.setVisible(false);
            }
        });
    }

    @FXML
    private void generate() {
        Trace trace = currentTrace.get();
        String language = languageChoice.getValue();
        if(("B".equals(language) && tfMachine.getText().isEmpty()) || tfIncludedMachine.getText().isEmpty() || language == null || language.isEmpty()) {
            return;
        }
        String result = traceGenerator.generateAllOperations(tfMachine.getText(), tfIncludedMachine.getText(), Utils.getGroup(language), trace.getTransitionList());
        taGeneration.setText(result);
        currentTrace.set(trace);
    }

}
