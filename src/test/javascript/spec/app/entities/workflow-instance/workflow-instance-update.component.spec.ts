/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import WorkflowInstanceUpdateComponent from '@/entities/workflow-instance/workflow-instance-update.vue';
import WorkflowInstanceClass from '@/entities/workflow-instance/workflow-instance-update.component';
import WorkflowInstanceService from '@/entities/workflow-instance/workflow-instance.service';

import ApproverService from '@/entities/approver/approver.service';

import WorkflowTemplateService from '@/entities/workflow-template/workflow-template.service';

import DdUserService from '@/entities/dd-user/dd-user.service';

import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('WorkflowInstance Management Update Component', () => {
    let wrapper: Wrapper<WorkflowInstanceClass>;
    let comp: WorkflowInstanceClass;
    let workflowInstanceServiceStub: SinonStubbedInstance<WorkflowInstanceService>;

    beforeEach(() => {
      workflowInstanceServiceStub = sinon.createStubInstance<WorkflowInstanceService>(WorkflowInstanceService);

      wrapper = shallowMount<WorkflowInstanceClass>(WorkflowInstanceUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          workflowInstanceService: () => workflowInstanceServiceStub,

          approverService: () => new ApproverService(),

          workflowTemplateService: () => new WorkflowTemplateService(),

          ddUserService: () => new DdUserService(),

          publicCardDataService: () => new PublicCardDataService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.workflowInstance = entity;
        workflowInstanceServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(workflowInstanceServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.workflowInstance = entity;
        workflowInstanceServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(workflowInstanceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundWorkflowInstance = { id: 123 };
        workflowInstanceServiceStub.find.resolves(foundWorkflowInstance);
        workflowInstanceServiceStub.retrieve.resolves([foundWorkflowInstance]);

        // WHEN
        comp.beforeRouteEnter({ params: { workflowInstanceId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.workflowInstance).toBe(foundWorkflowInstance);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
