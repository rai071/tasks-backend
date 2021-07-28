package br.com.taskbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.taskbackend.model.Task;

public interface TaskRepo extends JpaRepository<Task, Long>{

}
