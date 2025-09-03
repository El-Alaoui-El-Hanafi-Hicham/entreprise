import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterByStatus',
  standalone: true
})
export class FilterByStatusPipe implements PipeTransform {

    transform(tasks: any[], status: string): any[] {
      console.log('Filtering tasks by status:', status, tasks.some(task => String(task.status).toLocaleLowerCase() === status.toLocaleLowerCase()));
    if (!tasks || !status) {
      return tasks;
    }
    return tasks.filter(task => String(task.status).toLocaleLowerCase() === status.toLocaleLowerCase());
  }

}
