package task_app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task_app.model.Task;


@Repository
public interface TaskDao extends JpaRepository<Task,Integer> {

}
