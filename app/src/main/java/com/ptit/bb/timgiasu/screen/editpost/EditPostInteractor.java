package com.ptit.bb.timgiasu.screen.editpost;

import com.gemvietnam.base.viper.Interactor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.FirebaseDatabase;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.PostDTO;

/**
 * The EditPost interactor
 */
class EditPostInteractor extends Interactor<EditPostContract.Presenter>
        implements EditPostContract.Interactor {

    EditPostInteractor(EditPostContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void savePost(String city, String id, PostDTO mPost, OnCompleteListener<Void> onCompleteListener) {
        FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(city).child(DBConstan.POSTS).child(id).setValue(mPost).addOnCompleteListener(onCompleteListener);
    }
}
