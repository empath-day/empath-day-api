package com.empathday.empathdayapi.infrastructure.schedule.todo;

import com.empathday.empathdayapi.domain.schedule.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
