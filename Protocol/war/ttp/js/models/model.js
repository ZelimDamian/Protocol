directory.EncryptedProduct = Backbone.Model.extend({

    defaults:function() {
    	return {
			name: '',
			description: '',
			content: '',
			enSymKey: '',
			timeStamp: '',
			cert: '',
			orderId: ''
    	};
    }
});

directory.Payment = Backbone.Model.extend({

    urlRoot:"/rest/ttp/payments",

    defaults:function() {
    	return {
			payer: '',
			payerName: '',
			payee: '',
			productId: '',
			amount: '',
			timeStamp: '',
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