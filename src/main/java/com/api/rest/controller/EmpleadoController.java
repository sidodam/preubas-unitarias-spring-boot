package com.api.rest.controller;


import com.api.rest.model.Empleado;
import com.api.rest.service.EmlpeadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmlpeadoService empleadoService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Empleado guardarEmpleado(@RequestBody Empleado empleado) {
        return empleadoService.saveEmpleado(empleado);

    }

    @GetMapping
    public List<Empleado> listarEmpleados() {
        return empleadoService.getAllEmpleados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoById(@PathVariable("id") Long id) {
        return empleadoService.getEmpleadoById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable("id") Long id, @RequestBody Empleado empleado) {
        Optional<Empleado> empleadoOptional = empleadoService.getEmpleadoById(id);
        if (!empleadoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        empleado.setId(id);
        Empleado empleadoActualizado = empleadoService.updateEmpleado(empleado);
        return ResponseEntity.ok(empleadoActualizado);
    }

}