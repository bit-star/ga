/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import FormFieldDetailComponent from '@/entities/form-field/form-field-details.vue';
import FormFieldClass from '@/entities/form-field/form-field-details.component';
import FormFieldService from '@/entities/form-field/form-field.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('FormField Management Detail Component', () => {
    let wrapper: Wrapper<FormFieldClass>;
    let comp: FormFieldClass;
    let formFieldServiceStub: SinonStubbedInstance<FormFieldService>;

    beforeEach(() => {
      formFieldServiceStub = sinon.createStubInstance<FormFieldService>(FormFieldService);

      wrapper = shallowMount<FormFieldClass>(FormFieldDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { formFieldService: () => formFieldServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFormField = { id: 123 };
        formFieldServiceStub.find.resolves(foundFormField);

        // WHEN
        comp.retrieveFormField(123);
        await comp.$nextTick();

        // THEN
        expect(comp.formField).toBe(foundFormField);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFormField = { id: 123 };
        formFieldServiceStub.find.resolves(foundFormField);

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
