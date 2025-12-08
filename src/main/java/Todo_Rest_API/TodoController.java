package Todo_Rest_API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo){
        return todoService.createTodo(todo);
    }

    @GetMapping
    public List<Todo> getAllTodo(){
        return todoService.getAllTodo();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getById(@PathVariable(value = "id") Long todoID){
        return todoService.getById(todoID)
                .map(todo -> ResponseEntity.ok().body(todo))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /todos/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }




}
