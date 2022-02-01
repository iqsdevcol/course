package com.iqs.emma.course.controller;

import com.iqs.emma.course.dto.StateDto;
import com.iqs.emma.course.service.StateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<StateDto> getStates() {
        return statesService.getActiveStates();
    }


}
