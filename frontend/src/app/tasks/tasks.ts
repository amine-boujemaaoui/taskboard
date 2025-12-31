import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TaskService } from './task.service';
import { Task } from './task.model';

@Component({
  selector: 'app-tasks',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tasks.html',
})
export class Tasks implements OnInit {
  tasks: Task[] = [];
  loading = true;

  // formulaire
  title = '';
  description = '';
  status = 'TODO';
  priority = 'MEDIUM';

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks(): void {
    this.loading = true;
    this.taskService.list().subscribe({
      next: (data) => {
        this.tasks = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      },
    });
  }

  createTask(): void {
    if (!this.title.trim()) return;

    this.taskService.create({
      title: this.title,
      description: this.description || null,
      status: this.status,
      priority: this.priority,
    }).subscribe({
      next: () => {
        this.title = '';
        this.description = '';
        this.loadTasks();
      },
    });
  }
}
