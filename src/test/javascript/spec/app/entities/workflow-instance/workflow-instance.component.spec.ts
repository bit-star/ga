/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import WorkflowInstanceComponent from '@/entities/workflow-instance/workflow-instance.vue';
import WorkflowInstanceClass from '@/entities/workflow-instance/workflow-instance.component';
import WorkflowInstanceService from '@/entities/workflow-instance/workflow-instance.service';

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
  describe('WorkflowInstance Management Component', () => {
    let wrapper: Wrapper<WorkflowInstanceClass>;
    let comp: WorkflowInstanceClass;
    let workflowInstanceServiceStub: SinonStubbedInstance<WorkflowInstanceService>;

    beforeEach(() => {
      workflowInstanceServiceStub = sinon.createStubInstance<WorkflowInstanceService>(WorkflowInstanceService);
      workflowInstanceServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<WorkflowInstanceClass>(WorkflowInstanceComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          workflowInstanceService: () => workflowInstanceServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      workflowInstanceServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllWorkflowInstances();
      await comp.$nextTick();

      // THEN
      expect(workflowInstanceServiceStub.retrieve.called).toBeTruthy();
      expect(comp.workflowInstances[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      workflowInstanceServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeWorkflowInstance();
      await comp.$nextTick();

      // THEN
      expect(workflowInstanceServiceStub.delete.called).toBeTruthy();
      expect(workflowInstanceServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
