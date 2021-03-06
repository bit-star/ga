/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import FormFieldComponent from '@/entities/form-field/form-field.vue';
import FormFieldClass from '@/entities/form-field/form-field.component';
import FormFieldService from '@/entities/form-field/form-field.service';

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
  describe('FormField Management Component', () => {
    let wrapper: Wrapper<FormFieldClass>;
    let comp: FormFieldClass;
    let formFieldServiceStub: SinonStubbedInstance<FormFieldService>;

    beforeEach(() => {
      formFieldServiceStub = sinon.createStubInstance<FormFieldService>(FormFieldService);
      formFieldServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<FormFieldClass>(FormFieldComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          formFieldService: () => formFieldServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      formFieldServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllFormFields();
      await comp.$nextTick();

      // THEN
      expect(formFieldServiceStub.retrieve.called).toBeTruthy();
      expect(comp.formFields[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      formFieldServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeFormField();
      await comp.$nextTick();

      // THEN
      expect(formFieldServiceStub.delete.called).toBeTruthy();
      expect(formFieldServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
