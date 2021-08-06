import { IWorkflowTemplate } from '@/shared/model/workflow-template.model';

export interface ILinkSystem {
  id?: number;
  name?: string | null;
  workflowTemplates?: IWorkflowTemplate[] | null;
}

export class LinkSystem implements ILinkSystem {
  constructor(public id?: number, public name?: string | null, public workflowTemplates?: IWorkflowTemplate[] | null) {}
}
