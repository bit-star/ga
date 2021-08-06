import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IFormField } from '@/shared/model/form-field.model';

import FormFieldService from './form-field.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class FormField extends Vue {
  @Inject('formFieldService') private formFieldService: () => FormFieldService;
  private removeId: number = null;

  public formFields: IFormField[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllFormFields();
  }

  public clear(): void {
    this.retrieveAllFormFields();
  }

  public retrieveAllFormFields(): void {
    this.isFetching = true;
    this.formFieldService()
      .retrieve()
      .then(
        res => {
          this.formFields = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IFormField): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeFormField(): void {
    this.formFieldService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('gaApp.formField.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllFormFields();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
