directory.Payment = Backbone.Model.extend({
	defaults: function(){
		return {
			amount : 0,
			payer: '',
			payee: '',
			hash: '',
			enHash: '',
			publicKey: '',
			privateKey: '',
			payerName: ''
		};
	}
});

directory.Customer = Backbone.Model.extend({

    urlRoot:"/rest/bank/clients",

    defaults:function() {
    	return {
    		login : '',
    		account : ''
    	};
    }
});

directory.CustomerCollection = Backbone.Collection.extend({

    model: directory.Customer,

    url:"/rest/bank/clients"

});