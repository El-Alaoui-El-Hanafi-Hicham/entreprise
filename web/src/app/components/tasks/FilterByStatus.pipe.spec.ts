import { FilterByStatusPipe } from "./filterByStatus.pipe";

describe('KanbanPipelinePipe', () => {
  it('create an instance', () => {
    const pipe = new FilterByStatusPipe();
    expect(pipe).toBeTruthy();
  });
});
