import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task } from './task.model';

export type CreateTaskRequest = {
  title: string;
  description?: string | null;
  status: string;
  priority: string;
};

@Injectable({ providedIn: 'root' })
export class TaskService {
  private readonly apiUrl = 'http://localhost:8080/api/tasks';

  constructor(private http: HttpClient) {}

  list(): Observable<Task[]> {
    return this.http.get<Task[]>(this.apiUrl);
  }

  create(req: CreateTaskRequest): Observable<Task> {
    return this.http.post<Task>(this.apiUrl, req);
  }
}
