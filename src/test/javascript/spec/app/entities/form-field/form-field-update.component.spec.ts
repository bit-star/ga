/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import FormFieldUpdateComponent from '@/entities/form-field/form-field-update.vue';
import FormFieldClass from '@/entities/form-field/form-field-update.component';
import FormFieldService from '@/entities/form-field/form-field.service';

import WorkflowTemplateService from '@/entities/workflow-template/workflow-template.service';

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
  describe('FormField Management Update Component', () => {
    let wrapper: Wrapper<FormFieldClass>;
    let comp: FormFieldClass;
    let formFieldServiceStub: SinonStubbedInstance<FormFieldService>;

    beforeEach(() => {
      formFieldServiceStub = sinon.createStubInstance<FormFieldService>(FormFieldService);

      wrapper = shallowMount<FormFieldClass>(FormFieldUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          formFieldService: () => formFieldServiceStub,

          workflowTemplateService: () => new WorkflowTemplateService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.formField = entity;
        formFieldServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(formFieldServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.formField = entity;
        formFieldServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(formFieldServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFormField = { id: 123 };
        formFieldServiceStub.find.resolves(foundFormField);
        formFieldServiceStub.retrieve.resolves([foundFormField]);

        // WHEN
        comp.beforeRouteEnter({ params: { formFieldId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.formField).toBe(foundFormField);
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
