package com.api.rest.repositroy;

import com.api.rest.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRerpository extends JpaRepository<Empleado  , Long> {
}

