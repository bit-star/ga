import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const LinkSystem = () => import('@/entities/link-system/link-system.vue');
// prettier-ignore
const LinkSystemUpdate = () => import('@/entities/link-system/link-system-update.vue');
// prettier-ignore
const LinkSystemDetails = () => import('@/entities/link-system/link-system-details.vue');
// prettier-ignore
const WorkflowTemplate = () => import('@/entities/workflow-template/workflow-template.vue');
// prettier-ignore
const WorkflowTemplateUpdate = () => import('@/entities/workflow-template/workflow-template-update.vue');
// prettier-ignore
const WorkflowTemplateDetails = () => import('@/entities/workflow-template/workflow-template-details.vue');
// prettier-ignore
const FormField = () => import('@/entities/form-field/form-field.vue');
// prettier-ignore
const FormFieldUpdate = () => import('@/entities/form-field/form-field-update.vue');
// prettier-ignore
const FormFieldDetails = () => import('@/entities/form-field/form-field-details.vue');
// prettier-ignore
const OperationResults = () => import('@/entities/operation-results/operation-results.vue');
// prettier-ignore
const OperationResultsUpdate = () => import('@/entities/operation-results/operation-results-update.vue');
// prettier-ignore
const OperationResultsDetails = () => import('@/entities/operation-results/operation-results-details.vue');
// prettier-ignore
const GroupMembers = () => import('@/entities/group-members/group-members.vue');
// prettier-ignore
const GroupMembersUpdate = () => import('@/entities/group-members/group-members-update.vue');
// prettier-ignore
const GroupMembersDetails = () => import('@/entities/group-members/group-members-details.vue');
// prettier-ignore
const Conversation = () => import('@/entities/conversation/conversation.vue');
// prettier-ignore
const ConversationUpdate = () => import('@/entities/conversation/conversation-update.vue');
// prettier-ignore
const ConversationDetails = () => import('@/entities/conversation/conversation-details.vue');
// prettier-ignore
const DdUser = () => import('@/entities/dd-user/dd-user.vue');
// prettier-ignore
const DdUserUpdate = () => import('@/entities/dd-user/dd-user-update.vue');
// prettier-ignore
const DdUserDetails = () => import('@/entities/dd-user/dd-user-details.vue');
// prettier-ignore
const WorkflowInstance = () => import('@/entities/workflow-instance/workflow-instance.vue');
// prettier-ignore
const WorkflowInstanceUpdate = () => import('@/entities/workflow-instance/workflow-instance-update.vue');
// prettier-ignore
const WorkflowInstanceDetails = () => import('@/entities/workflow-instance/workflow-instance-details.vue');
// prettier-ignore
const PublicCardData = () => import('@/entities/public-card-data/public-card-data.vue');
// prettier-ignore
const PublicCardDataUpdate = () => import('@/entities/public-card-data/public-card-data-update.vue');
// prettier-ignore
const PublicCardDataDetails = () => import('@/entities/public-card-data/public-card-data-details.vue');
// prettier-ignore
const PrivateCardData = () => import('@/entities/private-card-data/private-card-data.vue');
// prettier-ignore
const PrivateCardDataUpdate = () => import('@/entities/private-card-data/private-card-data-update.vue');
// prettier-ignore
const PrivateCardDataDetails = () => import('@/entities/private-card-data/private-card-data-details.vue');
// prettier-ignore
const Approver = () => import('@/entities/approver/approver.vue');
// prettier-ignore
const ApproverUpdate = () => import('@/entities/approver/approver-update.vue');
// prettier-ignore
const ApproverDetails = () => import('@/entities/approver/approver-details.vue');
// prettier-ignore
const ConfirmCard = () => import('@/entities/confirm-card/confirm-card.vue');
// prettier-ignore
const ConfirmCardUpdate = () => import('@/entities/confirm-card/confirm-card-update.vue');
// prettier-ignore
const ConfirmCardDetails = () => import('@/entities/confirm-card/confirm-card-details.vue');
// prettier-ignore
const AlertCard = () => import('@/entities/alert-card/alert-card.vue');
// prettier-ignore
const AlertCardUpdate = () => import('@/entities/alert-card/alert-card-update.vue');
// prettier-ignore
const AlertCardDetails = () => import('@/entities/alert-card/alert-card-details.vue');
// prettier-ignore
const ApiInvokeLog = () => import('@/entities/api-invoke-log/api-invoke-log.vue');
// prettier-ignore
const ApiInvokeLogUpdate = () => import('@/entities/api-invoke-log/api-invoke-log-update.vue');
// prettier-ignore
const ApiInvokeLogDetails = () => import('@/entities/api-invoke-log/api-invoke-log-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/link-system',
    name: 'LinkSystem',
    component: LinkSystem,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/link-system/new',
    name: 'LinkSystemCreate',
    component: LinkSystemUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/link-system/:linkSystemId/edit',
    name: 'LinkSystemEdit',
    component: LinkSystemUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/link-system/:linkSystemId/view',
    name: 'LinkSystemView',
    component: LinkSystemDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/workflow-template',
    name: 'WorkflowTemplate',
    component: WorkflowTemplate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/workflow-template/new',
    name: 'WorkflowTemplateCreate',
    component: WorkflowTemplateUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/workflow-template/:workflowTemplateId/edit',
    name: 'WorkflowTemplateEdit',
    component: WorkflowTemplateUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/workflow-template/:workflowTemplateId/view',
    name: 'WorkflowTemplateView',
    component: WorkflowTemplateDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/form-field',
    name: 'FormField',
    component: FormField,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/form-field/new',
    name: 'FormFieldCreate',
    component: FormFieldUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/form-field/:formFieldId/edit',
    name: 'FormFieldEdit',
    component: FormFieldUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/form-field/:formFieldId/view',
    name: 'FormFieldView',
    component: FormFieldDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/operation-results',
    name: 'OperationResults',
    component: OperationResults,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/operation-results/new',
    name: 'OperationResultsCreate',
    component: OperationResultsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/operation-results/:operationResultsId/edit',
    name: 'OperationResultsEdit',
    component: OperationResultsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/operation-results/:operationResultsId/view',
    name: 'OperationResultsView',
    component: OperationResultsDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/group-members',
    name: 'GroupMembers',
    component: GroupMembers,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/group-members/new',
    name: 'GroupMembersCreate',
    component: GroupMembersUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/group-members/:groupMembersId/edit',
    name: 'GroupMembersEdit',
    component: GroupMembersUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/group-members/:groupMembersId/view',
    name: 'GroupMembersView',
    component: GroupMembersDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/conversation',
    name: 'Conversation',
    component: Conversation,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/conversation/new',
    name: 'ConversationCreate',
    component: ConversationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/conversation/:conversationId/edit',
    name: 'ConversationEdit',
    component: ConversationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/conversation/:conversationId/view',
    name: 'ConversationView',
    component: ConversationDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-user',
    name: 'DdUser',
    component: DdUser,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-user/new',
    name: 'DdUserCreate',
    component: DdUserUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-user/:ddUserId/edit',
    name: 'DdUserEdit',
    component: DdUserUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-user/:ddUserId/view',
    name: 'DdUserView',
    component: DdUserDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/workflow-instance',
    name: 'WorkflowInstance',
    component: WorkflowInstance,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/workflow-instance/new',
    name: 'WorkflowInstanceCreate',
    component: WorkflowInstanceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/workflow-instance/:workflowInstanceId/edit',
    name: 'WorkflowInstanceEdit',
    component: WorkflowInstanceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/workflow-instance/:workflowInstanceId/view',
    name: 'WorkflowInstanceView',
    component: WorkflowInstanceDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/public-card-data',
    name: 'PublicCardData',
    component: PublicCardData,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/public-card-data/new',
    name: 'PublicCardDataCreate',
    component: PublicCardDataUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/public-card-data/:publicCardDataId/edit',
    name: 'PublicCardDataEdit',
    component: PublicCardDataUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/public-card-data/:publicCardDataId/view',
    name: 'PublicCardDataView',
    component: PublicCardDataDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/private-card-data',
    name: 'PrivateCardData',
    component: PrivateCardData,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/private-card-data/new',
    name: 'PrivateCardDataCreate',
    component: PrivateCardDataUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/private-card-data/:privateCardDataId/edit',
    name: 'PrivateCardDataEdit',
    component: PrivateCardDataUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/private-card-data/:privateCardDataId/view',
    name: 'PrivateCardDataView',
    component: PrivateCardDataDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/approver',
    name: 'Approver',
    component: Approver,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/approver/new',
    name: 'ApproverCreate',
    component: ApproverUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/approver/:approverId/edit',
    name: 'ApproverEdit',
    component: ApproverUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/approver/:approverId/view',
    name: 'ApproverView',
    component: ApproverDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/confirm-card',
    name: 'ConfirmCard',
    component: ConfirmCard,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/confirm-card/new',
    name: 'ConfirmCardCreate',
    component: ConfirmCardUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/confirm-card/:confirmCardId/edit',
    name: 'ConfirmCardEdit',
    component: ConfirmCardUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/confirm-card/:confirmCardId/view',
    name: 'ConfirmCardView',
    component: ConfirmCardDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/alert-card',
    name: 'AlertCard',
    component: AlertCard,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/alert-card/new',
    name: 'AlertCardCreate',
    component: AlertCardUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/alert-card/:alertCardId/edit',
    name: 'AlertCardEdit',
    component: AlertCardUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/alert-card/:alertCardId/view',
    name: 'AlertCardView',
    component: AlertCardDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/api-invoke-log',
    name: 'ApiInvokeLog',
    component: ApiInvokeLog,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/api-invoke-log/new',
    name: 'ApiInvokeLogCreate',
    component: ApiInvokeLogUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/api-invoke-log/:apiInvokeLogId/edit',
    name: 'ApiInvokeLogEdit',
    component: ApiInvokeLogUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/api-invoke-log/:apiInvokeLogId/view',
    name: 'ApiInvokeLogView',
    component: ApiInvokeLogDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
