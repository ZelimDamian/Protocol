directory.TrustedPartyView = Backbone.View.extend({

    initialize: function () {
    	_.bindAll(this);
    	this.model = new directory.Payment();
    },

    render: function () {
    	if(typeof(this.productId) !== 'undefined')
    		this.model.set('productId', this.productId);
    	
        this.$el.html(this.template(this.model.toJSON()));
        
        this.$customer = this.$('#payer');
        this.$payerName = this.$("#payerName");
        this.$merchant = this.$('#payee');
        this.$productId = this.$('#productId');
        this.$amount = this.$('#amount');
        this.$enHash = this.$('#enHash');
        this.$publicKey = this.$('#publicKey');
        this.$privateKey = this.$('#privateKey');
        this.$timestamp = this.$('#timestamp');
        
        return this;
    },

    initiateProtocol: function()
    {
    	var payment = {
    			payer: this.$customer.val(),
    			payerName: this.$payerName.val(),
    			payee: this.$merchant.val(),
    			productId: this.$productId.val(),
    			amount: this.$amount.val(),
    			timeStamp: this.$timestamp.val(),
    			enHash: this.$enHash.val(),
    			publicKey: this.$publicKey.text(),
    			privateKey: this.$privateKey.text()
    	};
    	
    	this.sendRequestToMerchant(payment);
    },
    
    initiateProtocolJSON: function()
    {
    	var json = this.$('#jsonPayment').val();
    	var payment = $.parseJSON(json);
    	
    	this.model.set(payment);
    	
    	this.model.set('jsondata', json);
    	
    	this.render();
    },
    
    sendRequestToMerchant: function(data)
    {
    	$.ajax({
  		  url:"/rest/ttp/initiate/",
  		  type:"POST",
  		  data:JSON.stringify(data),
  		  contentType:"application/json; charset=utf-8",
  		  dataType:"json"
  		}).done(this.successMerchantCallback).fail(this.errorMerchantCallback);
	},
  
	successMerchantCallback: function(payment)
	{
		directory.homeView.renderKeyExchangeView(payment);
	},
	
	errorMerchantCallback: function(data, error)
	{
		this.$el.append("Error!!!" + JSON.stringify(error));
	},
    
    events: {
    	"click #initiateProtocol": 'initiateProtocol',
    	"click #initiateProtocolJSON": 'initiateProtocolJSON'
    }

});

directory.KeyExchangeView = Backbone.View.extend({
	initialize: function () {
    	_.bindAll(this);
    	this.model = new directory.EncryptedProduct();
    },

    events: {
    	"click #makePaymentButton": "makePayment"
    },
    
    render: function () {
        this.$el.html(this.template(this.model.toJSON()));
        
    	this.$("#afterPaymentContainer").hide();
        
        this.$name = this.$('#name');
        this.$description = this.$('#description');
        this.$content = this.$('#content');
        this.$enSymKey = this.$('#enSymKey');
        this.$timeStamp = this.$('#timeStamp');
        
        return this;
    },
    
    makePayment: function()
    {
    	$.ajax({
    		  url:"/rest/ttp/continue/" + this.model.get("orderHash"),
    		  type:"POST",
    		  data:{},
    		  contentType:"application/json; charset=utf-8",
    		  dataType:"text"
    		}).done(this.success).fail(this.error);
    },
    
    success: function(privateKey){
    	this.$("#makePaymentButton").prop('disabled', true).addClass("disabled");
    	this.$("#privateKeyContainer").html(privateKey);
    	this.$("#afterPaymentContainer").slideDown();
    },
    
    error: function(error, status){
    	this.$("#afterPaymentContainer").html("AN ERROR HAS OCCURRED!").addClass("text-error").slideDown();
    },
});