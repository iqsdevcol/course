package com.iqs.emma.course.controller;

import com.iqs.emma.course.domain.CityModel;
import com.iqs.emma.course.service.StateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/states")
@CrossOrigin(origins = "*")
public class StateController {


    private final StateService statesService;

    public StateController(StateService statesService) {
        this.statesService = statesService;
    }

    @GetMapping
    @ResponseBody
    public Map<String, List<CityModel>> getStates() {
        return statesService.getActiveStates();
    }


}
