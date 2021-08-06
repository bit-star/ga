/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import WorkflowTemplateDetailComponent from '@/entities/workflow-template/workflow-template-details.vue';
import WorkflowTemplateClass from '@/entities/workflow-template/workflow-template-details.component';
import WorkflowTemplateService from '@/entities/workflow-template/workflow-template.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('WorkflowTemplate Management Detail Component', () => {
    let wrapper: Wrapper<WorkflowTemplateClass>;
    let comp: WorkflowTemplateClass;
    let workflowTemplateServiceStub: SinonStubbedInstance<WorkflowTemplateService>;

    beforeEach(() => {
      workflowTemplateServiceStub = sinon.createStubInstance<WorkflowTemplateService>(WorkflowTemplateService);

      wrapper = shallowMount<WorkflowTemplateClass>(WorkflowTemplateDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { workflowTemplateService: () => workflowTemplateServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundWorkflowTemplate = { id: 123 };
        workflowTemplateServiceStub.find.resolves(foundWorkflowTemplate);

        // WHEN
        comp.retrieveWorkflowTemplate(123);
        await comp.$nextTick();

        // THEN
        expect(comp.workflowTemplate).toBe(foundWorkflowTemplate);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundWorkflowTemplate = { id: 123 };
        workflowTemplateServiceStub.find.resolves(foundWorkflowTemplate);

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
