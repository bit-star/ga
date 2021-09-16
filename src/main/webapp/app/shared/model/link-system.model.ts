import { IWorkflowTemplate } from '@/shared/model/workflow-template.model';
import { IApiClient } from '@/shared/model/api-client.model';

export interface ILinkSystem {
  id?: number;
  name?: string | null;
  workflowTemplates?: IWorkflowTemplate[] | null;
  apiClients?: IApiClient[] | null;
}

export class LinkSystem implements ILinkSystem {
  constructor(
    public id?: number,
    public name?: string | null,
    public workflowTemplates?: IWorkflowTemplate[] | null,
    public apiClients?: IApiClient[] | null
  ) {}
}
