import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFormField } from '@/shared/model/form-field.model';
import FormFieldService from './form-field.service';

@Component
export default class FormFieldDetails extends Vue {
  @Inject('formFieldService') private formFieldService: () => FormFieldService;
  public formField: IFormField = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.formFieldId) {
        vm.retrieveFormField(to.params.formFieldId);
      }
    });
  }

  public retrieveFormField(formFieldId) {
    this.formFieldService()
      .find(formFieldId)
      .then(res => {
        this.formField = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
