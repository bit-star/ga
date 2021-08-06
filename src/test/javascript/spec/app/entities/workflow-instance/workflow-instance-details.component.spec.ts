/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import WorkflowInstanceDetailComponent from '@/entities/workflow-instance/workflow-instance-details.vue';
import WorkflowInstanceClass from '@/entities/workflow-instance/workflow-instance-details.component';
import WorkflowInstanceService from '@/entities/workflow-instance/workflow-instance.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('WorkflowInstance Management Detail Component', () => {
    let wrapper: Wrapper<WorkflowInstanceClass>;
    let comp: WorkflowInstanceClass;
    let workflowInstanceServiceStub: SinonStubbedInstance<WorkflowInstanceService>;

    beforeEach(() => {
      workflowInstanceServiceStub = sinon.createStubInstance<WorkflowInstanceService>(WorkflowInstanceService);

      wrapper = shallowMount<WorkflowInstanceClass>(WorkflowInstanceDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { workflowInstanceService: () => workflowInstanceServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundWorkflowInstance = { id: 123 };
        workflowInstanceServiceStub.find.resolves(foundWorkflowInstance);

        // WHEN
        comp.retrieveWorkflowInstance(123);
        await comp.$nextTick();

        // THEN
        expect(comp.workflowInstance).toBe(foundWorkflowInstance);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundWorkflowInstance = { id: 123 };
        workflowInstanceServiceStub.find.resolves(foundWorkflowInstance);

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
