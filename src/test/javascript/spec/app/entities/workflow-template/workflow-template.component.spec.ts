/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import WorkflowTemplateComponent from '@/entities/workflow-template/workflow-template.vue';
import WorkflowTemplateClass from '@/entities/workflow-template/workflow-template.component';
import WorkflowTemplateService from '@/entities/workflow-template/workflow-template.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('WorkflowTemplate Management Component', () => {
    let wrapper: Wrapper<WorkflowTemplateClass>;
    let comp: WorkflowTemplateClass;
    let workflowTemplateServiceStub: SinonStubbedInstance<WorkflowTemplateService>;

    beforeEach(() => {
      workflowTemplateServiceStub = sinon.createStubInstance<WorkflowTemplateService>(WorkflowTemplateService);
      workflowTemplateServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<WorkflowTemplateClass>(WorkflowTemplateComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          workflowTemplateService: () => workflowTemplateServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      workflowTemplateServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllWorkflowTemplates();
      await comp.$nextTick();

      // THEN
      expect(workflowTemplateServiceStub.retrieve.called).toBeTruthy();
      expect(comp.workflowTemplates[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      workflowTemplateServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeWorkflowTemplate();
      await comp.$nextTick();

      // THEN
      expect(workflowTemplateServiceStub.delete.called).toBeTruthy();
      expect(workflowTemplateServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
