import { IWorkflowTemplate } from '@/shared/model/workflow-template.model';

export interface IFormField {
  id?: number;
  fieldName?: string | null;
  oaId?: string | null;
  fielddbtype?: string | null;
  labelName?: string | null;
  detailtable?: string | null;
  show?: boolean | null;
  isCardField?: boolean | null;
  isOaField?: boolean | null;
  orderNum?: number | null;
  workflowTemplate?: IWorkflowTemplate | null;
}

export class FormField implements IFormField {
  constructor(
    public id?: number,
    public fieldName?: string | null,
    public oaId?: string | null,
    public fielddbtype?: string | null,
    public labelName?: string | null,
    public detailtable?: string | null,
    public show?: boolean | null,
    public isCardField?: boolean | null,
    public isOaField?: boolean | null,
    public orderNum?: number | null,
    public workflowTemplate?: IWorkflowTemplate | null
  ) {
    this.show = this.show ?? false;
    this.isCardField = this.isCardField ?? false;
    this.isOaField = this.isOaField ?? false;
  }
}
