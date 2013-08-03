directory.CustomerView = Backbone.View.extend({

    render: function () {
    	this.model = new directory.Customer();
        this.$el.html(this.template(this.model.toJSON()));
        return this;
    }
});

directory.PaymentView = Backbone.View.extend({
	render: function() {
		this.$el.html(this.template(this.model.toJSON()));
		this.$('#jsonPayment').text(JSON.stringify(this.model.toJSON()));
		return this;
	}
});

directory.CustomerSummaryView = Backbone.View.extend({

    initialize:function (options) {
    	_.bindAll(this);
    	this.model = new directory.Customer();
        this.model.on("change", this.render, this);
        this.collection = options.collection;
        this.paymentView = new directory.PaymentView({model: new directory.Payment()});
    },

    render:function (model) {
    	if(model !== undefined)
    		this.model = model;
    	
        this.$el.html(this.template(this.model.toJSON()));
        
        this.$login = this.$el.find('#login');
        this.$account = this.$el.find('#account');
        this.$payment = this.$el.find('#paymentContainer');
        
        this.$amount = this.$('#amount');
        this.$payee = this.$('#payee');
        
        return this;
    },

    updateSaveModel:function()
    {
    	var data = {
    			login : this.$login.val(),
    			account: parseInt(this.$account.val())
    	};
    	this.model.save(data);
    	this.collection.add(this.model);
    },

    setModel:function(newModel)
    {
    	this.model = newModel;
    	this.render();
    },
    
    generatePayment: function()
    {
    	var payer = this.model.id;
    	if(typeof(payer) === undefined)
    	{
    		console.error("Unable to generate payment"); 
    		return;
    	}
    	
    	var data = {
    			amount : this.$amount.val(),
    			payee : this.$payee.val(),
    			payer: payer,
    			hash : ''
    	};
    	
    	$.ajax({
    		  url:"/rest/bank/payments/",
    		  type:"POST",
    		  data:JSON.stringify(data),
    		  contentType:"application/json; charset=utf-8",
    		  dataType:"json"
    		}).done(this.successCallback).fail(this.errorCallback);
	},
    
	successCallback: function(payment)
	{
		this.paymentView.model.set(payment);
        this.$("#paymentContainer").hide().html(this.paymentView.render().el).slideDown();
	},
	
	errorCallback: function(data, error)
	{
		this.$payment.text("Error!!!" + JSON.stringify(error));
	},
	
    events: {
    	'click #generateButton': 'generatePayment'
    }

});