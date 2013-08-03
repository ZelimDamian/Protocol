

directory.Payment = Backbone.Model.extend({

    urlRoot:"/rest/ttp/payments",

    defaults:function() {
    	return {
			payer: '',
			payee: '',
			productId: '',
			enHash: '',
			publicKey: '',
			privateKey: '',
			jsondata: ''
    	};
    }
});

directory.PaymentCollection = Backbone.Collection.extend({

    model: directory.Payment,

    url:"/rest/ttp/payments"

});