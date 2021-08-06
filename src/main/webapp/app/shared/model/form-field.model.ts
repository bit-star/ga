import { IWorkflowTemplate } from '@/shared/model/workflow-template.model';

export interface IFormField {
  id?: number;
  name?: string | null;
  value?: string | null;
  lable?: string | null;
  workflowTemplate?: IWorkflowTemplate | null;
}

export class FormField implements IFormField {
  constructor(
    public id?: number,
    public name?: string | null,
    public value?: string | null,
    public lable?: string | null,
    public workflowTemplate?: IWorkflowTemplate | null
  ) {}
}
