package io.github.oliviercap.chefduplacard.adapters.web.aliment.getaliments.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.aliment.getaliments.GetAlimentsViewModel;
import io.github.oliviercap.chefduplacard.application.aliment.getaliments.GetAlimentsRequestModel;
import io.github.oliviercap.chefduplacard.application.aliment.getaliments.ports.IGetAlimentsInputPort;
import io.github.oliviercap.chefduplacard.application.aliment.getaliments.ports.IGetAlimentsOutputPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetAlimentController {

    private final IGetAlimentsInputPort inputPort;
    private final IGetAlimentsOutputPort outputPort;

    public GetAlimentController(IGetAlimentsInputPort inputPort,
                                IGetAlimentsOutputPort outputPort) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }

    @GetMapping("/api/getAliments")
    GetAlimentsViewModel getAliment(

    ){
        inputPort.execute(new GetAlimentsRequestModel());
        return outputPort.getViewModel();
    }
}
