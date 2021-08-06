/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import WorkflowTemplateUpdateComponent from '@/entities/workflow-template/workflow-template-update.vue';
import WorkflowTemplateClass from '@/entities/workflow-template/workflow-template-update.component';
import WorkflowTemplateService from '@/entities/workflow-template/workflow-template.service';

import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';

import FormFieldService from '@/entities/form-field/form-field.service';

import LinkSystemService from '@/entities/link-system/link-system.service';

import WorkflowInstanceService from '@/entities/workflow-instance/workflow-instance.service';

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
  describe('WorkflowTemplate Management Update Component', () => {
    let wrapper: Wrapper<WorkflowTemplateClass>;
    let comp: WorkflowTemplateClass;
    let workflowTemplateServiceStub: SinonStubbedInstance<WorkflowTemplateService>;

    beforeEach(() => {
      workflowTemplateServiceStub = sinon.createStubInstance<WorkflowTemplateService>(WorkflowTemplateService);

      wrapper = shallowMount<WorkflowTemplateClass>(WorkflowTemplateUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          workflowTemplateService: () => workflowTemplateServiceStub,

          publicCardDataService: () => new PublicCardDataService(),

          formFieldService: () => new FormFieldService(),

          linkSystemService: () => new LinkSystemService(),

          workflowInstanceService: () => new WorkflowInstanceService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.workflowTemplate = entity;
        workflowTemplateServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(workflowTemplateServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.workflowTemplate = entity;
        workflowTemplateServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(workflowTemplateServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundWorkflowTemplate = { id: 123 };
        workflowTemplateServiceStub.find.resolves(foundWorkflowTemplate);
        workflowTemplateServiceStub.retrieve.resolves([foundWorkflowTemplate]);

        // WHEN
        comp.beforeRouteEnter({ params: { workflowTemplateId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.workflowTemplate).toBe(foundWorkflowTemplate);
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
