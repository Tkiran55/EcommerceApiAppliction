package Todo_Rest_API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public Todo createTodo(Todo todo){
        return todoRepository.save(todo);
    }

    public List<Todo> getAllTodo(){
        return todoRepository.findAll();
    }

    public Optional<Todo> getById(Long id){
        return todoRepository.findById(id);
    }

    public void deleteTodo(Long id){
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        todoRepository.delete(todo);
    }

}
