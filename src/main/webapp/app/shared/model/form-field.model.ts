import { IWorkflowTemplate } from '@/shared/model/workflow-template.model';

export interface IFormField {
  id?: number;
  fieldname?: string | null;
  value?: string | null;
  fielddbtype?: string | null;
  labelname?: string | null;
  fieldlabel?: string | null;
  detailtable?: string | null;
  show?: boolean | null;
  orderNum?: number | null;
  workflowTemplate?: IWorkflowTemplate | null;
}

export class FormField implements IFormField {
  constructor(
    public id?: number,
    public fieldname?: string | null,
    public value?: string | null,
    public fielddbtype?: string | null,
    public labelname?: string | null,
    public fieldlabel?: string | null,
    public detailtable?: string | null,
    public show?: boolean | null,
    public orderNum?: number | null,
    public workflowTemplate?: IWorkflowTemplate | null
  ) {
    this.show = this.show ?? false;
  }
}
